package Core;

import Account.AccountProvider;
import Review.ReviewProvider;
import Book.BookListProvider;
import Book.BookProvider;
import Receipt.ReceiptProvider;
import Branch.BranchProvider;

public class Mediator {
   private AccountProvider ACCOUNT;
   private ReviewProvider REVIEW;
   private BookProvider BOOK;
   private BookListProvider BOOKLIST;
   private ReceiptProvider RECEIPT;
   private BranchProvider BRANCH;
}
