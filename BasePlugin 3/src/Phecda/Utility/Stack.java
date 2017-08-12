package Phecda.Utility;

public class Stack<E> {

	private BidirectionalNode<E> top;
	private BidirectionalNode<E> bottom;
	
	public void push(E data) {
		BidirectionalNode<E> node = new BidirectionalNode<E>(data);
		if (isEmpty()) {
			bottom = node;
			top = node;
			return;
		} else {
			top.setNext(null);
			node.setPrevious(top);
			top = node;
		}
	}
	
	public E pop() {
		if (isEmpty()) return null;
		BidirectionalNode<E> node = top;
		top = top.getPrevious();
		top.setNext(null);
		return node.getData();
	}
	
	public boolean isEmpty() {
		return top == null;
	}
}
