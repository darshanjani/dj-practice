export class NodeDetail {
  id: string;
  description: string;
  pending: number;
  completed: number;

  constructor(id: string, description: string, pending: number, completed: number) {
    this.id = id;
    this.description = description;
    this.pending = pending;
    this.completed = completed;
  }
}
