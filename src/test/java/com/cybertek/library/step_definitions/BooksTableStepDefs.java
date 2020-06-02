package com.cybertek.library.step_definitions;

import com.cybertek.library.pages.BooksPage;
import com.cybertek.library.pojos.Book;
import com.cybertek.library.utilities.BrowserUtils;
import com.cybertek.library.utilities.DBUtils;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BooksTableStepDefs {
    BooksPage booksPage = new BooksPage();

    @Then("search table should contain results matching {}")
    public void search_table_should_contain_results_matching(String book) {
      List<String> actualRows = BrowserUtils.getElementsText(booksPage.allRows);

      boolean found = true;
      for(String row : actualRows){
          if(!row.contains(book)){
              found = false;
              break;
          }
      }
      assertTrue(book + "was not found in books table", found);
    }

    @When("I open/edit book {}")
    public void i_edit_book_The_kiterunner(String book) {
        System.out.println("book = " + book);
        BrowserUtils.waitForClickability(booksPage.search, 5).sendKeys(book);
        BrowserUtils.waitForClickability(booksPage.editBook(book), 5).click();
    }

    // register a data table type that uses a custom class
    @DataTableType
    public Book convertBook(Map<String, String> dataTable){
        Book book = new Book(dataTable.get("name"), dataTable.get("author"), dataTable.get("year"));
        return book;
    }

    @Then("I verify book information")
    public void i_verify_book_information(Book book) {
        System.out.println(book);
        System.out.println("author " + book.getAuthor());
        System.out.println("name " + book.getName());
        System.out.println("year " + book.getYear());

        assertEquals("Book name did not match",
                book.getName(), booksPage.bookName.getAttribute("value"));
        assertEquals("Book author did not match",
                book.getAuthor(), booksPage.author.getAttribute("value"));
        assertEquals("Book year did not match",
                book.getYear(), booksPage.year.getAttribute("value"));
    }


    @Then("book information must match the database for {}")
    public void book_information_must_match_the_database_for_The_kite_runner(String book) {
        String aName = booksPage.bookName.getAttribute("value");
        String aAuthor = booksPage.author.getAttribute("value");
        String aYear = booksPage.year.getAttribute("value");
        String aIsbn = booksPage.isbn.getAttribute("value");
        String aCategory = booksPage.categoryList().getFirstSelectedOption().getText();
        String aDescription = booksPage.description.getAttribute("value");

        // get the book info from database
        String sql = "SELECT b.name, b.isbn, b.year, b.author, bc.name, b.description\n" +
                "FROM books b\n" +
                "JOIN book_categories bc\n" +
                "ON b.book_category_id = bc.id\n" +
                "WHERE b.name = '" + book + "';";
        System.out.println(sql);

        Map<String, Object>  dbData = DBUtils.getRowMap(sql);

        String eAuthor = dbData.get("author").toString();
        String eYear = dbData.get("year").toString();
        String eIsbn = dbData.get("isbn").toString();
        String eDescription = dbData.get("description").toString();
        String eCat = dbData.get("name").toString();

        assertEquals("author did not match", eAuthor, aAuthor);
        assertEquals("year did not match", eYear, aYear);
        assertEquals("isbn did not match", eIsbn, aIsbn);
        assertEquals("desctiption did not match", eDescription, aDescription);
        assertEquals("category did not match", eCat, aCategory);
    }
}
