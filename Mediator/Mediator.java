package Mediator;

// Import 
import Account.AccountProvider;
import Review.ReviewProvider;
import Book.BookListProvider;
import Book.BookProvider;
import Receipt.ReceiptProvider;
import Branch.BranchProvider;

public class Mediator {
   private AccountProvider accProvider;
   private ReviewProvider revProvider;
   private BookProvider bookProvider;
   private BookListProvider bkLstProvider;
   private ReceiptProvider recProvider;
   private BranchProvider brProvider;
}