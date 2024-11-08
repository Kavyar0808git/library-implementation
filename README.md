 User can view books in library 
Scenario: As a User 
I want to see the books present in the library 
So that I can chose which book to borrow 
Given, there are no books in the library 
When, I view the books in the library 
Then, I see an empty library 
Given, there are books in the library 
When, I view the books in the library 
Then, I see the list of books in the library  
2. User can borrow a book from the library 
Given, there are books in the library 
When, I choose a book to add to my borrowed list 
Then, the book is added to my borrowed list 
And, the book is removed from the library 
Note:  
a. Each User has a borrowing limit of 2 books at any point of time 
3. User can borrow a copy of a book from the library 
Given, there are more than one copy of a book in the library 
When, I choose a book to add to my borrowed list 
Then, one copy of the book is added to my borrowed list 
And, the library has at least one copy of the book left 
Given, there is only one copy of a book in the library 
When, I choose a book to add to my borrowed list 
Then, one copy of the book is added to my borrowed list 
And, the book is removed from the library 
Note: 
a. Only 1 copy of a book can be borrowed by a User at any point of time  
4. User can return books to the library 
Given, I have 2 books in my borrowed list 
When, I return one book to the library 
Then, the book is removed from my borrowed list 
And, the library reflects the updated stock of the book 
Given, I have 2 books in my borrowed list 
When, I return both books to the library 
Then, my borrowed list is empty 
And, the library reflects the updated stock of the books



Library Application feature: 

Admin Login:
1) Welcome screen -> Select Login for all users.
2) Login to Library -> Provide User name and password added in DB.  
3) Login using admin credentials. 
4) We can add new book and view the list of books in Library and its available copies. Here we can view total number of copies available for the book. 
5) Back to admin page on Admin logged in screen to return to main page. 

Admin User: Add new book;
1) Follow step 1 to 3 from Admin Login scenario.
2) Click on Add new book.
3) Provide the book name and total number of copies for that book.
4) Click on Add book. 
5) Now we can view the new book is displayed in the list of books with available copies for the same.

User Login: 
1) Follow step 1 to 2 from Admin Login scenario.
2) Login using non-admin or normal user credentials. 
3) We enter into logged in screen for user,
4) User can click on Library or Borrowed List screen. 
5) If User click on Library button.
6) User can view list of books which available more than 0. 
7) User can select 2 books among the displayed list and add to borrowed list.
8) If User selects more than 2 books then we see popup saying only 2 books allowed to borrow.
