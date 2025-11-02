### Notable Changes

Find all method now returns a page, where it is recommended to return to APIs or to Front-end consume when have a lot of data.

The Page is useful because it returns just data to who is requesting needs, saving unnecessary memory.

It returns beyond the requested data: 
- Number of pages.
- Number of elements by page.
- Number of all elements.

Note: A class BookDataLoader was created to auto insert some books to ever run
(data.sql with dll-auto=update wasn't working properly).

