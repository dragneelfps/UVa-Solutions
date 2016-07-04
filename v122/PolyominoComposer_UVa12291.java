package v122;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;


public class PolyominoComposer_UVa12291 {

	public static void main(String[] args) throws IOException 
	{
		Scanner sc = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);

		while(true)
		{
			int n = sc.nextInt(), m = sc.nextInt();
			if(n + m == 0)
				break;
			char[][] large = new char[n + m][n + m], small = new char[m][m];
			for(int i = 0; i < m; ++i)
				for(int j = 0; j < n + m; ++j)
					large[i][j] = '.';
			for(int i = m; i < n + m; ++i)
			{
				char[] s = sc.next().toCharArray();
				for(int j = 0; j < m; ++j)
					large[i][j] = '.';
				for(int j = m; j < n + m; ++j)
					large[i][j] = j-m >= s.length ? '.' : s[j-m];
			}
			
			for(int i = 0; i < m; ++i)
			{
				char[] s = sc.next().toCharArray();
				for(int j = 0; j < m; ++j)
					small[i][j] = j >= s.length ? '.' : s[j];
			}
			
			for(int i = 0; i < n + m; ++i)
				for(int j = 0; j < n + m; ++j)
					match(large, small, n + m, m, i, j);
			
			out.println(clear(large));
		}
		out.flush();
		out.close();
	}
	
	static void match(char[][] g1, char[][] g2, int n, int m, int i, int j)
	{
		if(i + m > n || j + m > n)
			return;
		boolean match = true;
		for(int x = i; x < i + m; ++x)
			for(int y = j; y < j + m; ++y)
				if(g1[x][y] == '.' && g2[x-i][y-j] == '*')
					match = false;
		if(match)
			for(int x = i; x < i + m; ++x)
				for(int y = j; y < j + m; ++y)
					if(g2[x-i][y-j] == '*')
						g1[x][y] = '.';
	}
	
	static int clear(char[][] x)
	{
		for(char[] y: x)
			for(char z: y)
				if(z != '.')
					return 0;
		return 1;
	}

	static class Scanner 
	{
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream s){	br = new BufferedReader(new InputStreamReader(s));}

		public String next() throws IOException 
		{
			while (st == null || !st.hasMoreTokens()) 
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public int nextInt() throws IOException {return Integer.parseInt(next());}

		public long nextLong() throws IOException {return Long.parseLong(next());}

		public String nextLine() throws IOException {return br.readLine();}

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

		public boolean ready() throws IOException {return br.ready();}


	}
}