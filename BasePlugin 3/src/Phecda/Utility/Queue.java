package Phecda.Utility;

public class Queue<E> {

	private BidirectionalNode<E> top;
	private BidirectionalNode<E> bottom;
	
	public void push(E data) {
		BidirectionalNode<E> node = new BidirectionalNode<E>(data);
		if (isEmpty()) {
			bottom = node;
			top = node;
			return;
		} else {
			bottom.setPrevious(node);
			node.setNext(bottom);
			bottom = node;
		}
	}
	
	public E pop() {
		if (isEmpty()) return null;
		BidirectionalNode<E> node = top;
		top = top.getPrevious();
		if (top != null) top.setNext(null);
		else bottom = null;
		return node.getData();
	}
	
	public E peek() {
		if (isEmpty()) return null;
		else return top.getData();
	}
	
	public boolean isEmpty() {
		return top == null;
	}
	
	
}
