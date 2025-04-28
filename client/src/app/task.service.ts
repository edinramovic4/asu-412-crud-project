import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from './task.model';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private baseUrl = 'http://localhost:8080/tasks';

  constructor(private http: HttpClient) {}

  getAllTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.baseUrl}`);
  }

  createTask(task: Partial<Task>): Observable<any> {
    return this.http.post(`${this.baseUrl}`, task);
  }

  updateTask(task: Task): Observable<any> {
    return this.http.put(`${this.baseUrl}/${task.id}`, task);
  }

  deleteTask(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  toggleTasks(taskIds: number[], completed: boolean): Observable<any> {
    const params = taskIds.reduce((acc, id) => acc.append('taskIds', id.toString()), new HttpParams())
      .set('completed', completed.toString());

    return this.http.put(`${this.baseUrl}/toggle`, null, { params });
  }

  createParentAndChild(parentTitle: string, childTitle: string): Observable<any> {
    const params = new HttpParams()
      .set('parentTitle', parentTitle)
      .set('childTitle', childTitle);

    return this.http.post(`${this.baseUrl}/parent-child`, {}, { params });
  }
}
