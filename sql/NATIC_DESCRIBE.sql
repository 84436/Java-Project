USE NATIC;

-- Tables: Show columns, data types, keys and extras
-- Since there's no equivalent of T-SQL "PRINT" here, we'll have to use ECHO (Windows) instead.

\! ECHO [Table: IDGen_States]
DESCRIBE IDGen_States;

\! ECHO [Table: Accounts]
DESCRIBE Accounts;

\! ECHO [Table: Customers]
DESCRIBE Customers;

\! ECHO [Table: Staff]
DESCRIBE Staff;

\! ECHO [Table: Branches]
DESCRIBE Branches;

\! ECHO [Table: Books]
DESCRIBE Books;

\! ECHO [Table: BranchStockLists]
DESCRIBE BranchStockLists;

\! ECHO [Table: CustomerLibraries]
DESCRIBE CustomerLibraries;

\! ECHO [Table: Receipts]
DESCRIBE Receipts;

\! ECHO [Table: Reviews]
DESCRIBE Reviews;

-- ID generators: show current states
\! ECHO [ID Generator States]
SELECT * FROM IDGen_States;
