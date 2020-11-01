export function items<T>(o: Record<string, T>): T[] {
  return Object.values<T>(o);
}
