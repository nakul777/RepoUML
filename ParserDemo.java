
import java.util.Collection;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.StringReader;

import java.io.*;
import java.nio.file.Files;

import com.sun.xml.internal.fastinfoset.vocab.ParserVocabulary;

import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import java.sql.*;

public class ParserDemo extends JPanel{
int i;
  /**
   * The main method demonstrates the easiest way to load a parser.
   * Simply call loadModel and specify the path, which can either be a
   * file or any resource in the classpath.  For example, this
   * demonstrates loading from the models jar file, which you need to
   * include in the classpath for ParserDemo to work.
   */

  public static void main(String[] args) {
    LexicalizedParser lp = LexicalizedParser.loadModel();
    if (args.length > 0) {
      demoDP(lp, args[0]);
    } else {
      try{
    	demoAPI(lp); 	
      }catch(Exception e)
      {
    	  System.out.println("Inside catch of demoAPI");  
      }
    }
  }

  /**
   * demoDP demonstrates turning a file into tokens and then parse
   * trees.  Note that the trees are printed by calling pennPrint on
   * the Tree object.  It is also possible to pass a PrintWriter to
   * pennPrint if you want to capture the output.
   */
  public static void demoDP(LexicalizedParser lp, String filename) {
    // This option shows loading and sentence-segmenting and tokenizing
    // a file using DocumentPreprocessor.
    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
    // You could also create a tokenizer here (as below) and pass it
    // to DocumentPreprocessor
    for (List<HasWord> sentence : new DocumentPreprocessor(filename)) {
      Tree parse = lp.apply(sentence);
      parse.pennPrint();
      System.out.println();
      
      GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
      Collection tdl = gs.typedDependenciesCCprocessed();
      System.out.println(tdl);
      System.out.println();
      int choice=2;
       sw11 panel = new sw11( choice );
      JFrame application = new JFrame(); // creates a new JFrame
      application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      application.add( panel ); // add the panel to the frame
      application.setSize( 300, 300 ); // set the desired size
      application.setVisible( true ); // show the frame
    
      WinApp aw=new WinApp();
      
    		 
    }
  }

  /**
   * demoAPI demonstrates other ways of calling the parser with
   * already tokenized text, or in some cases, raw text that needs to
   * be tokenized as a single sentence.  Output is handled with a
   * TreePrint object.  Note that the options used when creating the
   * TreePrint can determine what results to print out.  Once again,
   * one can capture the output by passing a PrintWriter to
   * TreePrint.printTree.
 * @throws SQLException 
   */
  public static String identifyFileTypeUsingFilesProbeContentType(final String fileName)
 	{
 	   String fileType = "Undetermined";
 	   final File file = new File(fileName);
 	   try
 	   {
 	      fileType = Files.probeContentType(file.toPath());
 	   }
 	   catch (IOException ioException)
 	   {
 	      System.out.println("ERROR: Unable to determine file type for " + fileName + " due to exception " + ioException);
 	   }
 	   return fileType;
 	}
  public static void demoAPI(LexicalizedParser lp) throws SQLException {
    // This option shows parsing a list of correctly tokenized words
    //String[] sent = { "This", "is", "an", "easy", "sentence", "." };
    String st2="/home/nakul/Desktop/stanford-parser-full-2013-06-20/data/a.txt";
     //ParsingFiles ide=new ParsingFiles();
    System.out.println("The Type of file being parsed is....");
    System.out.println(identifyFileTypeUsingFilesProbeContentType(st2));

    FileInputStream fSt=null;
    BufferedReader i=null;
   
    try
    {
    fSt=new FileInputStream(st2);
     i=new BufferedReader(new InputStreamReader(fSt));
    	// TODO: handle exception
	
     while(i.ready())
     {
    	 String str=i.readLine();
    	 System.out.println(i.readLine());
    	 //String str=new String();
   
    	 List<CoreLabel> rawWords = Sentence.toCoreLabelList(str);
    	 Tree parse = lp.apply(rawWords);
    	 parse.pennPrint();
    	 System.out.println();
    	 
    	 // This option shows loading and using an explicit tokenizer
    	 String sent2 = "";
    	 TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
    	 List<CoreLabel> rawWords2 =
    	tokenizerFactory.getTokenizer(new StringReader(str)).tokenize();
    	 parse = lp.apply(rawWords2);
  
    	 TreebankLanguagePack tlp = new PennTreebankLanguagePack();
    	 GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
    	 GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
    	 List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
    	 System.out.println(tdl);
    	 System.out.println();
    	 
    	 TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
    	 tp.printTree(parse);
    	 String temp=new String();
    	 temp="/home/nakul/FromProject/parsingoftext.txt";
    	 FileWriter fw=new FileWriter(temp);
    	 BufferedWriter bw=new BufferedWriter(fw);
    	 bw.write("File parsed:: "+ st2);
    	 
    	 bw.write(tdl.toString());
    	 bw.close();
    	 
    	 System.out.println("Output written to "+temp);
    	 
    	 System.out.println("Now Calling WordNet for word sense disambiguation");
    	 System.out.println("WORDNET'S INFORMATION FOR EACH WORD IN SENTENCE");
    	 System.out.println("...");
    	 
    	 String pathname = new String();
    	 int choice=2;
    	  sw11 panel = new sw11( choice );
    	 JFrame application = new JFrame(); // creates a new JFrame
    	 application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    	 application.add( panel ); // add the panel to the frame
    	 application.setSize( 600, 600 ); // set the desired size
    	 application.setVisible( true ); // show the frame
    	 
    	 String sfile=new String();
    	 // sfile="/home/nakul/Desktop/stanford-parser-full-2013-06-20/data/h.txt";
    	 
    	
    	 
    	 
    	 FileReader fw1=new FileReader(temp);
    	 BufferedReader bw1=new BufferedReader(fw1);
    	 
    	 System.out.println("Reading the file"+temp);
    	 String str22=new String();
    	 try{
    	 /*while(bw1.readLine()!="EOF")
    	 
    	 {
    		 str22=bw1.readLine();
    		 System.out.println(str22);
    	 }*/
    		 
    	 bw1.close();
    	 }catch (Exception e) {
			// TODO: handle exception
		}
    	 
    	 /* code to search in wordnet*/
    	 FileReader fr=new FileReader(sfile);
    	 BufferedReader br=new BufferedReader(fr);
    	 //    	 st3=br.readLine();
    	 //code to search word in word net to clarify its type here word contains the word from the file
    	 File file=new File(sfile);
    	 Scanner sc=new Scanner(file);
    	 temp="/home/nakul/FromProject/resultofwordnet.txt";
	    	FileWriter fw2=new FileWriter(temp);
	    	 BufferedWriter bw2=new BufferedWriter(fw2);
    	 while(sc.hasNext())
    	 {
    		 String word=sc.next();
    		 String pathname1="wn " + word;	 
    		 System.out.println("Now searching in wordnet :: " +word);
    	 
    	 String s=null;
    	try{
    			Process p=Runtime.getRuntime().exec(pathname1);
	
    			BufferedReader stdIn=new BufferedReader(new InputStreamReader(p.getInputStream()));
    			BufferedReader stderr=new BufferedReader(new InputStreamReader(p.getErrorStream()));
	
    			while ((s=stdIn.readLine())!=null)
    			{
    				System.out.println(s);
    				bw2.write(s);
    		    	 
    			}
    			//fout.write(b, 0, 1000);
	    		}catch (Exception e) {
    			System.out.println(e);
    			
	    		}
    	
    	 }
    	 //Code for scanning the FrameNet
    
    	 //Code to call Semulink
    	 
    	 /*String st3="/home/nakul/Desktop/stanford-parser-full-2013-06-20/FrameNet propbank-1.7/+ .xml";
    	    FileInputStream fst=null;
    	    BufferedReader i1=null;
    	    try
    	    {
    	    fSt=new FileInputStream(st3);
    	     i1=new BufferedReader(new InputStreamReader(fst));
    	    	// TODO: handle exception
    		
    	     while(i.ready())
    	     {
    	    	 System.out.println(i);
    	     }
    	    }catch(Exception e)
    	    {
    	    	System.out.println(e);
    	    }*/   
}
     }catch(Exception e)
     {System.out.println("Inside catch of shop.xml");
    	 System.out.println(e);
    }
 
  
  }
  
  public void paintComponent( Graphics g )
  {
  super.paintComponent( g );			
  for ( int i = 0; i < 10; i++ )
  {
  // pick the shape based on the
    

      ArrayList<Integer> scores = new ArrayList<Integer>(10);

      Random r = new Random();

      for (int i1 : scores){
          i1 = r.nextInt(20);
          System.out.println(r);
      }

      int y1;
      int y2;

      for (int i1 = 0; i1 < scores.size(); i1++){
          y1 = scores.get(i1);
          y2 = scores.get(i1+1);
          g.drawLine(i1, y1, i1+1, y2);
      }
  }
  	g.drawRect( 10 + i , 10,50 + i , 10);
  	 
   
  g.drawOval( 10 + i , 10,50 + i , 10 );
  
	  
      //ssetJMenuBar(menubar);
  
  } // end switch
   // end for

  
 ParserDemo() {
	 //initialize();
	 
 } // static methods only
  
 
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Label l=new Label("label1");
	frame.add(l);
	}
	private JFrame frame;
}