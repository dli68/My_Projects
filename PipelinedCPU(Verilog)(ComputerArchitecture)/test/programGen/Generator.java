import java.io.PrintWriter;

public class Generator 
{
	public static void main(String[] args)
	{
		int k = 200;
		try
		{
			for(int j = 0; j < 20; j++, k++)
			{
				PrintWriter writer = new PrintWriter("combined"+k+".asm", "UTF-8");
				writer.println(GenerateLib.rfInit());
				for(int i = 0; i < 65500; i++)
				{
					writer.println(GenerateLib.selection(GenerateLib.ranGen.nextInt(11)));
				}
				writer.println(GenerateLib.hlt());
				writer.close();
			}
		}
		catch(Exception e)
		{
			System.exit(-1);
		}
	}
}
