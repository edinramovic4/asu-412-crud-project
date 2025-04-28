export interface Task {
  id: number;
  title: string;
  completed: boolean;
  parentId: number | null;
  lastUpdated: string;
}
