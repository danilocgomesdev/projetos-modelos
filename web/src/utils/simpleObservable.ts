export class Observer<T> {
  readonly id: string;
  private readonly func: (data: T) => void;

  constructor(id: string, func: (data: T) => void) {
    this.id = id;
    this.func = func;
  }

  notify(data: T): void {
    this.func(data);
  }
}

export class SimpleObservable<T> {
  private subscribers: Array<Observer<T>> = [];

  subscribe(func: (data: T) => void): Observer<T> {
    const observer = new Observer(crypto.randomUUID(), func);
    this.subscribers.push(observer);
    return observer;
  }

  unsubscribe(observer: Observer<T>): void {
    const index = this.subscribers.findIndex((sub) => sub.id === observer.id);
    if (index !== -1) {
      this.subscribers.splice(index, 1);
    }
  }

  notify(data: T): void {
    this.subscribers.forEach((observer) => observer.notify(data));
  }
}
