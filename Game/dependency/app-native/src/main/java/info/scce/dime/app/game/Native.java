package info.scce.dime.app.game;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

import de.ls5.dywa.generated.controller.dime__HYPHEN_MINUS__models.app.TableController;
import de.ls5.dywa.generated.controller.dime__HYPHEN_MINUS__models.app.TableRowController;
import de.ls5.dywa.generated.controller.dime__HYPHEN_MINUS__models.app.TableEntryController;
import de.ls5.dywa.generated.controller.dime__HYPHEN_MINUS__models.app.ValidationController;

import de.ls5.dywa.generated.entity.dime__HYPHEN_MINUS__models.app.Table;
import de.ls5.dywa.generated.entity.dime__HYPHEN_MINUS__models.app.TableRow;
import de.ls5.dywa.generated.entity.dime__HYPHEN_MINUS__models.app.TableEntry;
import de.ls5.dywa.generated.entity.dime__HYPHEN_MINUS__models.app.Validation;
import de.ls5.dywa.generated.entity.dime__HYPHEN_MINUS__models.app.WordOfTheDay;

import javax.inject.Inject;
import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Collection of static methods for native SIBs
 */
@ApplicationScoped
public class Native {
	@Inject
	protected TableController tController;
	@Inject
	protected TableRowController trController;
	@Inject
	protected TableEntryController teController;
	@Inject
	protected ValidationController validController;

	public static void setRow(TableRow tableRow, String word) {
		String[] wordArray = word.split("(?!^)");
		List<TableEntry> tableEntrys = tableRow.gettableEntry_TableEntry();
		for (int i = 0; i < wordArray.length; i++) {
			TableEntry currentTableEntry = tableEntrys.get(i);
			currentTableEntry.setvalue(wordArray[i]);
		}
	}

	public static Table createGameBoardWordle(long width, long height) {
		Native one = new Native();
		return one.init(width, height);

	}

	public Table init(long width, long height) {
		Table table = getBean(TableController.class).create(null);

		List<TableRow> tableRowList = new ArrayList<TableRow>();
		for (int x = 0; x < height; x++) {
			TableRow tableRow = getBean(TableRowController.class).create(null);
			System.out.println("inside for loop x =" + x);
			List<TableEntry> tableEntryList = new ArrayList<TableEntry>();
			for (int y = 0; y < width; y++) {

				System.out.println("inside nested for loop y =" + y);
				TableEntry tableEntry = getBean(TableEntryController.class).create(null);
				tableEntry.setvalue("");
				Validation validation = Validation.noMatch;
				tableEntry.setvalidation(validation);
				tableEntryList.add(tableEntry);
			}

			tableRow.settableEntry_TableEntry(tableEntryList);
			tableRowList.add(tableRow);
		}
		table.settableRow_TableRow(tableRowList);

		return table;
	}

	@SuppressWarnings("unchecked")
	private static <T> T getBean(Class<T> clazz) {
		final BeanManager beanManager = CDI.current().getBeanManager();

		final Bean<T> bean = (Bean<T>) beanManager.resolve(beanManager.getBeans(clazz));

		final CreationalContext<T> cctx = beanManager.createCreationalContext(bean);

		return (T) beanManager.getReference(bean, bean.getBeanClass(), cctx);
	}

	public static boolean isInList(int index, Object list) {
		return index >= 0 && index < ((List) list).size();
	}

	public static TableRow getNextRow(TableRow tableRow, List<TableRow> tableRows) {
		int index = tableRows.indexOf(tableRow) + 1;
		// print error
		System.err.println("index: " + index + " isInList = " + isInList(index, tableRows));
		return isInList(index, tableRows) ? tableRows.get(index) : null;
	}

	public static boolean isWordValid(String word) throws IOException {

		if (word.length() < 5) {
			return false;
		}

		InputStream inputStream = Native.class.getResourceAsStream("/words.txt");

		String[] words = new BufferedReader(new InputStreamReader(inputStream))
				.lines()
				.toArray(String[]::new);

		return Arrays.asList(words).contains(word);

		// check if the word is in the array as fast as possible

	}

	public static void matchRow(TableRow row, WordOfTheDay wordOfTheDay) {
		List<TableEntry> tableEntrys = row.gettableEntry_TableEntry();
		String word = wordOfTheDay.getvalue();
		for (int i = 0; i < tableEntrys.size(); i++) {
			String entry = tableEntrys.get(i).getvalue();

			Validation match;
			if (word.contains(entry)) {
				if (entry.charAt(0) == word.charAt(i)) {
					match = Validation.fullMatch;
				} else {
					System.out.print(entry.charAt(0) + "!=" + word.charAt(i));
					match = Validation.letterMatch;
				}
			} else {
				match = Validation.noMatch;
			}

			tableEntrys.get(i).setvalidation(match);
		}
	}

	public static boolean hasWon(TableRow row, WordOfTheDay wordOfTheDay) {
		List<TableEntry> tableEntrys = row.gettableEntry_TableEntry();
		String word = wordOfTheDay.getvalue();
		// convert list of tableEntrys to a string
		String tableEntryString = tableEntrys.stream().map(TableEntry::getvalue).collect(Collectors.joining());
		return tableEntryString.equals(word);
	}

	public static String getWordOfTheDay() throws IOException {
		ArrayList<String> words = new ArrayList<String>();

		InputStream inputStream = Native.class.getResourceAsStream("/wordle.txt");

		BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
		// read the file line by line into an array
		String line = bf.readLine();
		while (line != null) {
			words.add(line);
			line = bf.readLine();
		}
		bf.close();

		// get random word
		Random rand = new Random();
		int randomIndex = rand.nextInt(words.size());
		String word = words.get(randomIndex);

		System.out.println("word of the day is " + word);
		return word;
	}

}