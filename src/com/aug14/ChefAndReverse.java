package com.aug14;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * http://www.codechef.com/AUG14/problems/REVERSE
 * 
 * @author sultan.of.swing
 *
 */
public class ChefAndReverse {

	public PrintWriter mOut;
	public FasterScanner mFScanner;
	public EdgeWeightedDiGraph mDiGraph;
	public DijkstraShortestPath mPath;
	public boolean mMarked[];
	public int mDist;
	public int mN;

	public ChefAndReverse() {
		mOut = new PrintWriter(System.out);
		mFScanner = new FasterScanner();
	}

	public void solve() {
		int i;
		int u, v;
		int N, M;
		int dist;
		DirectedEdge edge;

		N = mFScanner.nextInt();
		M = mFScanner.nextInt();
		mDiGraph = new EdgeWeightedDiGraph(N);

		for (i = 0; i < M; i++) {
			u = mFScanner.nextInt() - 1;
			v = mFScanner.nextInt() - 1;
			edge = new DirectedEdge(u, v, 0);
			mDiGraph.addEdge(edge);
			edge = new DirectedEdge(v, u, 1);
			mDiGraph.addEdge(edge);
		}

		mPath = new DijkstraShortestPath(mDiGraph, 0);

		dist = mPath.distTo(N - 1);

		if (dist == Integer.MAX_VALUE)
			dist = -1;

		mOut.println(dist);

	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		ChefAndReverse mSol = new ChefAndReverse();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}

class DijkstraShortestPath {

	private DirectedEdge edgeTo[];
	private int distTo[];
	private boolean marked[];
	public Queue<Node> pq;
	public int V;

	public DijkstraShortestPath(EdgeWeightedDiGraph G, int source) {
		int i;
		int v;
		DirectedEdge edge;
		Node node;
		V = G.V();
		marked = new boolean[V];
		pq = new PriorityQueue<Node>(G.V(), new PriorityQueueComparator());

		distTo = new int[V];
		edgeTo = new DirectedEdge[V];
		for (i = 0; i < V; i++)
			distTo[i] = Integer.MAX_VALUE;

		distTo[source] = 0;
		edgeTo[source] = null;

		node = new Node(source, 0);
		// marked[source] = true;
		pq.add(node);

		while (!pq.isEmpty()) {
			node = pq.poll();
			v = node.vertex();
			if (marked[v])
				continue;
			for (DirectedEdge e : G.adj(v)) {
				relax(e);
			}
			marked[v] = true;
		}
	}

	public int distTo(int d) {
		return distTo[d];
	}

	public void relax(DirectedEdge edge) {
		int v;
		int w;
		Node node;

		v = edge.from();
		w = edge.to();

		if (edge.weight() + distTo[v] < distTo[w]) {
			distTo[w] = distTo[v] + edge.weight();
			edgeTo[w] = edge;
			node = new Node(w, distTo[w]);
			pq.add(node);
		}

	}

	private class Node {
		private int v;
		private int weight;

		public Node(int v, int weight) {
			this.v = v;
			this.weight = weight;
		}

		public int weight() {
			return weight;
		}

		public int vertex() {
			return v;
		}
	}

	private class PriorityQueueComparator implements Comparator<Node> {

		@Override
		public int compare(Node arg0, Node arg1) {
			// TODO Auto-generated method stub
			if (arg0.weight() < arg1.weight())
				return -1;
			else if (arg0.weight() > arg1.weight())
				return 1;

			return 0;
		}

	}

}

class DirectedEdge implements Comparable<DirectedEdge> {

	private int v;
	private int w;
	private int weight;

	public DirectedEdge(int v, int w, int weight) {
		this.v = v;
		this.w = w;
		this.weight = weight;
	}

	public int from() {
		return v;
	}

	public int to() {
		return w;
	}

	public int weight() {
		return weight;
	}

	@Override
	public int compareTo(DirectedEdge arg0) {
		// TODO Auto-generated method stub
		if (this.weight < arg0.weight)
			return -1;
		else if (this.weight > arg0.weight)
			return 1;
		return 0;
	}

}

class EdgeWeightedDiGraph {

	private int V;
	private int E;
	private Bag<DirectedEdge> adj[];

	public EdgeWeightedDiGraph(int V) {
		this.V = V;
		this.E = 0;
		adj = (Bag<DirectedEdge>[]) new Bag[V];
		for (int i = 0; i < V; i++) {
			adj[i] = new Bag<DirectedEdge>();
		}
	}

	public int E() {
		return E;
	}

	public int V() {
		return V;
	}

	public void addEdge(DirectedEdge e) {
		int v;
		v = e.from();
		adj[v].add(e);
		E++;
	}

	public Iterable<DirectedEdge> adj(int v) {
		return adj[v];
	}

	public Iterable<DirectedEdge> edges() {
		int v = 0;
		Bag<DirectedEdge> edges = new Bag<DirectedEdge>();

		for (v = 0; v < V; v++) {
			for (DirectedEdge edge : adj[v]) {
				edges.add(edge);
			}
		}
		return edges;
	}
}

class Bag<Item> implements Iterable<Item> {

	private Node first;
	private int N;

	private class Node {
		private Item item;
		private Node next;
	}

	public Bag() {
		first = null;
		N = 0;
	}

	public void add(Item w) {
		Node oldfirst = first;
		first = new Node();
		first.item = w;
		first.next = oldfirst;
		N++;
	}

	public boolean isEmpty() {
		return first == null;
	}

	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new ListIterator();
	}

	private class ListIterator implements Iterator<Item> {
		private Node current = first;

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return current != null;
		}

		@Override
		public Item next() {
			// TODO Auto-generated method stub
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}
	}
}