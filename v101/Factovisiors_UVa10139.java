package v101;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Factovisiors_UVa10139 {

	static ArrayList<Integer> primes;

	static void sieve(int N)
	{
		boolean[] isComposite = new boolean[N];
		primes = new ArrayList<Integer>(N / 10);
		for(int i = 2; i < N; ++i)
			if(!isComposite[i])
			{
				primes.add(i);
				if(1l * i * i < N)
					for(int j = i * i; j < N; j += i)
						isComposite[j] = true;
			}
	}

	static ArrayList<Pair> primeFactors(int N)
	{
		ArrayList<Pair> factors = new ArrayList<Pair>();

		int idx = 0, p = primes.get(0);
		while(1l * p * p <= N)
		{
			int e = 0;
			while(N % p == 0) 
			{ 
				N /= p; 
				++e; 
			}
			if(e != 0)
				factors.add(new Pair(p, e));
			p = primes.get(++idx);
		}
		if(N != 1)
			factors.add(new Pair(N, 1));
		return factors;
	}

	static boolean hasEnoughFactors(int N, ArrayList<Pair> factors)
	{
		for(Pair fac: factors)
		{
			int count = 0;
			long d = fac.p;
			while(d <= N)
			{
				count += N / d;
				d *= fac.p;
			}
			if(count < fac.e)
				return false;
		}
		return true;
	}
	
	public static void main(String[] args) throws Exception 
	{
		Scanner sc = new Scanner(System.in); 
		PrintWriter out = new PrintWriter(System.out);
		
		sieve(50000);
		while(sc.ready())
		{
			int N = sc.nextInt(), M = sc.nextInt();
			if(M != 0 && hasEnoughFactors(N, primeFactors(M)))
				out.printf("%d divides %d!\n", M, N);
			else
				out.printf("%d does not divide %d!\n", M, N);
		}
		out.flush();
		out.close();
	}

	static class Pair
	{
		int p, e;

		Pair(int x, int y) { p = x; e = y; }

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

		public boolean nextEmpty() throws IOException
		{
			String s = br.readLine();
			st = new StringTokenizer(s);
			return s.isEmpty();
		}


	}
}