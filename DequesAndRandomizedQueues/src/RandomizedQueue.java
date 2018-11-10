import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Object[] array;
	private int index = 0;
		
	public RandomizedQueue() {
		array = new Object[1];
	}
	
	public boolean isEmpty() {
		return index == 0;
	}
	
	public int size() {
		return index;
	}
	
	public void enqueue(Item item) {
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
	
		if (index == array.length) {
			resize(2*Math.min(0, array.length));
		}
		array[index++] = item;
	}
	
	public Item dequeue() {
		if (index == 0) {
			throw new java.lang.IllegalArgumentException();
		}
		
		int ranIndex = 	StdRandom.uniform(index);
		Item dequeueItem = (Item) array[ranIndex];
		
		// Move the last item to the position where a random item is removed	
		array[ranIndex] = array[--index];
		array[index] = null;
		
		if (index == array.length/4) {
			resize(array.length/2);
		}
		
		return dequeueItem;
	}
	
	public Item sample() {
		if (index == 0) {
			throw new java.util.NoSuchElementException();
		}
		
		int ranIndex = 	StdRandom.uniform(index);
		return (Item) array[ranIndex];
	}
	
	@Override
	public Iterator<Item> iterator() {
		
		return new DequeIterator();
	}	
	
	private class DequeIterator implements Iterator<Item> {

		int currentIndex = 0;
		int[] perm = StdRandom.permutation(index);
				
		@Override
		public boolean hasNext() {
			return index > currentIndex;
		}

		@Override
		public Item next() {
			
			if (!hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			
			return (Item) array[perm[currentIndex++]];
		}
		
		@Override
		public void remove() {
			
			throw new java.lang.UnsupportedOperationException();
		}
	}
		
	private void resize(int capacity) {	
		Object[] copy = new Object[capacity];
		
		for (int i = 0; i < index; i++) {
			copy[i] = array[i];
		}
		
		array = copy;
	}
	
	
	public static void main(String[] args) {
		RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
		
		q.enqueue(315);

		q.dequeue();
		q.enqueue(419);

	}
}
