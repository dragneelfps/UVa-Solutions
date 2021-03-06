package v121;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class CatsVsDogs_UVa12168 {

	static int V, n, m;	//n(left) + m(right) = V
	static ArrayList<Integer>[] adjList;	//size = n, idx = [1, n], val = [1, m]

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);

		int tc = sc.nextInt();
		while(tc-->0)
		{
			int c = sc.nextInt(), d = sc.nextInt();
			V = sc.nextInt();

			n = 0; m = 0;
			int[] idx = new int[V];
			String[] keep = new String[V], away = new String[V];
			for(int i = 0; i < V; ++i)
			{
				keep[i] = sc.next();
				away[i] = sc.next();
				if(keep[i].charAt(0) == 'C')
					idx[i] = ++n;
				else
					idx[i] = ++m;
			}

			if(n == V || m == V)
				out.println(V);
			else
			{
				
				adjList = new ArrayList[600];
				for(int i = 0; i < V; ++i)
					if(keep[i].charAt(0) == 'C')
					{
						adjList[idx[i]] = new ArrayList<Integer>();
						for(int j = 0; j < V; ++j)
							if(keep[i].equals(away[j]) || away[i].equals(keep[j]))
								adjList[idx[i]].add(idx[j]);
					}
				out.println(V - hopcroftKarp());
			}
		}

		out.flush();
		out.close();


	}

	static int[] pair_U, pair_V, dist;
	static final int NIL = 0, INF = (int)1e9;
	static int hopcroftKarp()
	{
		pair_U = new int[n + 1];	//filled with NIL
		pair_V = new int[m + 1];	//filled with NIL
		dist = new int[n + 1];

		int matching = 0;
		while(bfs())
			for(int u = 1; u <= n; ++u)
				if(pair_U[u] == NIL && dfs(u))
					++matching;
		return matching;
	}

	static boolean bfs()
	{
		Queue<Integer> q = new LinkedList<Integer>();
		for(int u = 1; u <= n; ++u)
			if(pair_U[u] == NIL)
			{
				dist[u] = 0;
				q.add(u);
			}
			else
				dist[u] = INF;
		dist[NIL] = INF;
		while(!q.isEmpty())
		{
			int u = q.remove();
			if(dist[u] < dist[NIL])
			{
				for(int v : adjList[u])
					if(dist[pair_V[v]] == INF)
					{
						dist[pair_V[v]] = dist[u] + 1;
						q.add(pair_V[v]);
					}
			}
		}
		return dist[NIL] != INF;
	}

	static boolean dfs(int u)
	{
		if(u == NIL)
			return true;

		for(int v : adjList[u])
			if(dist[pair_V[v]] == dist[u] + 1 && dfs(pair_V[v]))
			{
				pair_U[u] = v;
				pair_V[v] = u;
				return true;
			}
		dist[u] = INF;
		return false;


	}


	static class Scanner 
	{
		StringTokenizer st;
		BufferedReader br;

		public Scanner(FileReader fileReader) throws FileNotFoundException{	br = new BufferedReader(fileReader);}

		public Scanner(InputStream s) throws FileNotFoundException{	br = new BufferedReader(new InputStreamReader(s));}

		public String next() throws IOException 
		{
			while (st == null || !st.hasMoreTokens()) 
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public double nextDouble() throws IOException
		{
			String x = next();
			StringBuilder sb = new StringBuilder("0");
			double res = 0, f = 1;
			boolean dec = false, neg = false;
			int start = 0;
			if(x.charAt(0) == '-')
			{
				neg = true;
				start++;
			}
			for(int i = start; i < x.length(); i++)
				if(x.charAt(i) == '.')
				{
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				}
				else
				{
					sb.append(x.charAt(i));
					if(dec)
						f *= 10;
				}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg?-1:1);
		}


		public int nextInt() throws IOException {return Integer.parseInt(next());}

		public long nextLong() throws IOException {return Long.parseLong(next());}

		public String nextLine() throws IOException {return br.readLine();}

		public boolean ready() throws IOException {return br.ready(); }


	}

}


