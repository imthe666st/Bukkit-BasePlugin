package Phecda.Utility;

public class BidirectionalNode<F> {
	private F data;
	private BidirectionalNode<F> previous;
	private BidirectionalNode<F> next;
	
	public BidirectionalNode(F d) {
		this.data = d;
		this.previous = null;
		this.next = null;
	}
	
	public BidirectionalNode<F> getPrevious() {
		return previous;
	}
	public BidirectionalNode<F> getNext() {
		return next;
	}

	public boolean isFirst() {
		return previous == null;
	}
	public boolean isLast() {
		return previous == null;
	}
	
	public F getData() {
		return data;
	}

	public BidirectionalNode<F> setPrevious(BidirectionalNode<F> p) {
		previous = p;
		return p;
	}
	public BidirectionalNode<F> setNext(BidirectionalNode<F> p) {
		next = p;
		return p;
	}
}