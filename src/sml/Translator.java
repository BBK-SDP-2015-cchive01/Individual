package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/*
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 */
public class Translator {

	// word + line is the part of the current line that's not yet processed
	// word has no whitespace
	// If word and line are not empty, line begins with whitespace
	private String line = "";
	private Labels labels; // The labels of the program being translated
	private ArrayList<Instruction> program; // The program to be created
	private String fileName; // source file of SML code

	private static final String SRC = "src";

	public Translator(String fileName) {
		this.fileName = SRC + "/" + fileName;
	}

	// translate the small program in the file into lab (the labels) and
	// prog (the program)
	// return "no errors were detected"
	public boolean readAndTranslate(Labels lab, ArrayList<Instruction> prog) {

		try (Scanner sc = new Scanner(new File(fileName))) {
			// Scanner attached to the file chosen by the user
			labels = lab;
			labels.reset();
			program = prog;
			program.clear();

			try {
				line = sc.nextLine();
			} catch (NoSuchElementException ioE) {
				return false;
			}

			// Each iteration processes line and reads the next line into line
			while (line != null) {
				// Store the label in label
				String label = scan();

				if (label.length() > 0) {
					Instruction ins = getInstruction(label);
					if (ins != null) {
						labels.addLabel(label);
						program.add(ins);
					}
				}

				try {
					line = sc.nextLine();
				} catch (NoSuchElementException ioE) {
					return false;
				}
			}
		} catch (IOException ioE) {
			System.out.println("File: IO error " + ioE.getMessage());
			return false;
		}
		return true;
	}

	// line should consist of an MML instruction, with its label already
	// removed. Translate line into an instruction with label label
	// and return the instruction
	public Instruction getInstruction(String label) {

		if (line.equals(""))
			return null;

		String ins = scan();
		// Capitalises first letter of instruction to adhere to Java class naming conventions
		ins = Character.toUpperCase(ins.charAt(0)) + ins.substring(1);

		String nextReg;
		List<String> registerListStr = new ArrayList<>(); // Scan all register lists as Strings
		while (!(nextReg = scan()).equals("")) {
			registerListStr.add(nextReg);
		}

		Object inputParams[] = new Object[registerListStr.size() + 1];
		Class inputParamTypes []= new Class[registerListStr.size() + 1];

		inputParams[0] = label;
		inputParamTypes[0] = String.class;

		// Iterate through register list and try parsing as integers, if NumberFormatException thrown assume String
		for (int i = 0; i < registerListStr.size(); i++) {
			try {
				inputParams[i + 1] = Integer.parseInt(registerListStr.get(i));
				inputParamTypes[i + 1] = Integer.TYPE;
			} catch (NumberFormatException e) {
				inputParams[i + 1] = registerListStr.get(i);
				inputParamTypes[i + 1] = String.class;
			}
		}

		// Reflect class
		String packageName = this.getClass().getPackage().getName();
		String className = ins + "Instruction";
		try {
			Class c = Class.forName(packageName + "." + className);
			// Checks that class extends Instruction
			if (c.getSuperclass().equals(Instruction.class)) {
				// get the Constructor matching the format of the input parameters types
				Constructor matchingConstructor = c.getConstructor(inputParamTypes);
				return ((Instruction) matchingConstructor.newInstance(inputParams));

			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.err.println("Error: Method with that signature does not exist");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;


	/*	switch (ins) {
		case "add":
			r = scanInt();
			s1 = scanInt();
			s2 = scanInt();
			return new AddInstruction(label, r, s1, s2);
		case "lin":
			r = scanInt();
			x = scanInt();
			return new LinInstruction(label, r, x);
		case "sub":
			r = scanInt();
			s1 = scanInt();
			s2 = scanInt();
			return new SubInstruction(label, r, s1, s2);
		case "mul":
			r = scanInt();
			s1 = scanInt();
			s2 = scanInt();
			return new MulInstruction(label, r, s1, s2);
		case "div":
			r = scanInt();
			s1 = scanInt();
			s2 = scanInt();
			return new DivInstruction(label, r, s1, s2);
		case "out":
			r = scanInt();
			return new OutInstruction(label, r);
		case "bnz":
			s1 = scanInt();
			jumpToLabel = scan();
			return new BnzInstruction(label, s1, jumpToLabel);

		}*/

		// You will have to write code here for the other instructions.

	}

	/*
	 * Return the first word of line and remove it from line. If there is no
	 * word, return ""
	 */
	private String scan() {
		line = line.trim();
		if (line.length() == 0)
			return "";

		int i = 0;
		while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
			i = i + 1;
		}
		String word = line.substring(0, i);
		line = line.substring(i);
		return word;
	}

	// Return the first word of line as an integer. If there is
	// any error, return the maximum int
	private int scanInt() {
		String word = scan();
		if (word.length() == 0) {
			return Integer.MAX_VALUE;
		}

		try {
			return Integer.parseInt(word);
		} catch (NumberFormatException e) {
			return Integer.MAX_VALUE;
		}
	}
}