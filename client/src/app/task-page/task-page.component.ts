import { Component, OnInit } from '@angular/core';
import { TaskService } from '../task.service';
import { Task } from '../task.model';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-task-page',
  templateUrl: './task-page.component.html',
  styleUrls: ['./task-page.component.css'],
  imports: [FormsModule, CommonModule]
})
export class TaskPageComponent implements OnInit {

  tasks: Task[] = [];
  displayedTasks: any[] = [];
  selectedTab: string = 'all'; // ALL, ACTIVE, COMPLETED

  // Input Form fields
  newTaskTitle: string = '';
  parentTaskTitle: string = '';
  childTaskTitle: string = '';

  editingTaskId: number | null = null;
  editingTitle: string = '';

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void { // Grab all tasks from DB to populate table
    this.taskService.getAllTasks().subscribe(tasks => {
      this.tasks = tasks;
      this.organizeTasks();
    });
  }

  organizeTasks(): void {
    const taskMap = new Map<number, any>();

    this.tasks.forEach(task => {
      (task as any).children = [];
      taskMap.set(task.id, task);
    });

    const rootTasks: any[] = [];

    this.tasks.forEach(task => {
      if (task.parentId) {
        const parent = taskMap.get(task.parentId);
        if (parent) {
          parent.children.push(task);
        }
      } else {
        rootTasks.push(task);
      }
    });

    this.displayedTasks = this.applyTabFilter(rootTasks);
  }

  applyTabFilter(tasks: any[]): any[] {
    if (this.selectedTab === 'all') {
      return tasks;
    }

    const filterCompleted = (completed: boolean) => {
      return tasks
        .filter(task => task.completed === completed)
        .map(task => ({
          ...task,
          children: task.children.filter((child: Task) => child.completed === completed)
        }));
    };

    return this.selectedTab === 'active'
      ? filterCompleted(false)
      : filterCompleted(true);
  }

  filterTasks(tab: string): void {
    this.selectedTab = tab;
    this.organizeTasks(); // Filter again on tab switch
  }

  createSingleTask(): void {
    if (!this.newTaskTitle.trim()) return;

    const newTask: Partial<Task> = {
      title: this.newTaskTitle,
      completed: false,
      parentId: null
    };

    this.taskService.createTask(newTask).subscribe(() => {
      this.newTaskTitle = '';
      this.loadTasks();
    });
  }

  createParentAndChildTask(): void {
    if (!this.parentTaskTitle.trim() || !this.childTaskTitle.trim()) return;

    this.taskService.createParentAndChild(this.parentTaskTitle, this.childTaskTitle)
      .subscribe(() => {
        this.parentTaskTitle = '';
        this.childTaskTitle = '';
        this.loadTasks();
      });
  }

  toggleTaskCompleted(task: Task): void {
    task.completed = !task.completed;
    this.taskService.updateTask(task).subscribe(() => {
      this.loadTasks();
    });
  }

  toggleAllTasks(): void {
    const tasksToToggle: Task[] = [];

    this.displayedTasks.forEach(task => {
      tasksToToggle.push(task);
      tasksToToggle.push(...task.children);
    });

    const targetCompleted = this.selectedTab === 'active';
    const ids = tasksToToggle.map(t => t.id);

    this.taskService.toggleTasks(ids, targetCompleted).subscribe(() => {
      this.loadTasks();
    });
  }

  startEdit(task: Task): void {
    this.editingTaskId = task.id;
    this.editingTitle = task.title;
  }

  saveEdit(task: Task): void {
    if (!this.editingTitle.trim()) return;

    task.title = this.editingTitle;
    this.taskService.updateTask(task).subscribe(() => {
      this.editingTaskId = null;
      this.editingTitle = '';
      this.loadTasks();
    });
  }

  cancelEdit(): void {
    this.editingTaskId = null;
    this.editingTitle = '';
  }

  deleteTask(task: Task): void {
    this.taskService.deleteTask(task.id).subscribe(() => {
      this.loadTasks();
    });
  }
}
