import java.util.Random;

public class GenerateLib 
{
	public static Random ranGen = new Random();
	public static int low = -1;
	public static int high = 0;
	public static String add()
	{
		String rtn = "add "+"R"+rGen()+", R"+rGen()+", R"+rGen();
		return rtn;
	}
	public static String sub()
	{
		String rtn = "sub "+"R"+rGen()+", R"+rGen()+", R"+rGen();
		return rtn;
	}
	public static String addz()
	{
		String rtn = "addz "+"R"+rGen()+", R"+rGen()+", R"+rGen();
		return rtn;
	}
	public static String and()
	{
		String rtn = "and "+"R"+rGen()+", R"+rGen()+", R"+rGen();
		return rtn;
	}
	public static String nor()
	{
		String rtn = "nor "+"R"+rGen()+", R"+rGen()+", R"+rGen();
		return rtn;
	}
	public static String sll()
	{
		String rtn = "sll "+"R"+rGen()+", R"+rGen()+", "+quadGen();
		return rtn;
	}
	public static String srl()
	{
		String rtn = "srl "+"R"+rGen()+", R"+rGen()+", "+quadGen();
		return rtn;
	}
	public static String sra()
	{
		String rtn = "sra "+"R"+rGen()+", R"+rGen()+", "+quadGen();
		return rtn;
	}
	
	/* since singlecycle is bugged*/
	public static String lw()
	{
		int i = rGen();
		int j = rGen();
		while(i == j)
		{
			j = rGen();
		}
		String rtn = "lw "+"R"+i+", R"+j+", "+signGen();
		return rtn;
	}
	public static String sw()
	{
		String rtn = "sw "+"R"+rGen()+", R"+rGen()+", "+signGen();
		return rtn;
	}
	public static String lhb()
	{
		String rtn = "lhb "+"R"+rGen()+", "+octGen();
		return rtn;
	}
	public static String llb()
	{
		String rtn = "lhb "+"R"+rGen()+", "+octGen();
		return rtn;
	}
	public static String hlt()
	{
		String rtn = "hlt";
		return rtn;
	}
	public static String llbr(int i)
	{
		String rtn = "lhb "+"R"+i+", "+octGen();
		return rtn;
	}
	public static String jr()
	{
		String rtn = "jr R"+rGen();
		return rtn;
	}
	
	public static String selection(int i)
	{
		switch(i)
		{
		case 0: return add();
		case 1: return addz();
		case 2: return sub();
		case 3: return and();
		case 4: return nor();
		//case 5: return sll();
		//case 6: return srl();
		case 11: return jr();
		case 9: return lw();
		case 10: return sw();
		case 7: return lhb();
		case 8: return llb();
		default: return "\n";
		}
	}
	public static String rfInit()
	{
		String rtn = "";
		rtn += llbr(1)+"\n";
		rtn += llbr(2)+"\n";
		rtn += llbr(3)+"\n";
		rtn += llbr(4)+"\n";
		rtn += llbr(5)+"\n";
		rtn += llbr(6)+"\n";
		rtn += llbr(7)+"\n";
		rtn += llbr(8)+"\n";
		rtn += llbr(9)+"\n";
		rtn += llbr(10)+"\n";
		rtn += llbr(11)+"\n";
		rtn += llbr(12)+"\n";
		rtn += llbr(13)+"\n";
		rtn += llbr(14)+"\n";
		rtn += llbr(15)+"\n";
		return rtn;
	}
	
	public static String memInit()
	{
		low++;
		if(low == 256)
		{
			low = 0;
			high++;
		}
		String rtn = "llb R1, "+low+"\n"+"lhb R1, "+high;
		return rtn;
	}
	
	private static int rGen()
	{
		return ranGen.nextInt(15)+1;
	}
	private static int quadGen()
	{
		return ranGen.nextInt(16);
	}
	private static int octGen()
	{
		return ranGen.nextInt(256);
	}
	private static String signGen()
	{
		String rtn = "";
		int i = ranGen.nextInt(2);
		if(i == 0)
		{
			rtn += "-"+(ranGen.nextInt(7)+1);
		}
		else
		{
			rtn += ranGen.nextInt(9);
		}
		return rtn;
	}
}
