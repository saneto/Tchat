import { from, forkJoin, of } from 'rxjs';
import { bufferCount, concatMap, delay } from 'rxjs/operators';

// Mock API call (replace with your real HTTP call)
function apiCall(id: number) {
  return of(`Result ${id}`).pipe(delay(100)); // Simulate async call
}

// Create an array of 100 items (API call IDs)
const apiCalls = Array.from({ length: 100 }, (_, i) => i + 1);

// Process in batches of 5 sequentially
from(apiCalls).pipe(
  bufferCount(5), // Group into batches of 5
  concatMap(batch =>
    forkJoin(batch.map(id => apiCall(id))) // Wait for all 5 in batch
  )
).subscribe({
  next: result => console.log('Batch result:', result),
  complete: () => console.log('All batches complete')
});