// example routes
/*
getFlightInfoList "/blog/@year/@month/@day/@title", forward: "/WEB-INF/groovy/blog.groovy?year=@year&month=@month&day=@day&title=@title"
getFlightInfoList "/something", redirect: "/blog/2008/10/20/something", cache: 2.hours
getFlightInfoList "/book/isbn/@isbn", forward: "/WEB-INF/groovy/book.groovy?isbn=@isbn", validate: { isbn ==~ /\d{9}(\d|X)/ }
*/

// routes for the blobstore service example
/*
getFlightInfoList "/upload",  forward: "/upload.gtpl"
getFlightInfoList "/success", forward: "/success.gtpl"
getFlightInfoList "/failure", forward: "/failure.gtpl"*/
