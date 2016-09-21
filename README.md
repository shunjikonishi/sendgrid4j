# Deprecated!
Because now Sendgrid prepares their own official library for Java.

[sendgrid-java](https://github.com/sendgrid/sendgrid-java)

Though this library works well now, this doesn't use new version of SendgridAPI(API v3).
So, this might not work someday.

sendgrid4j
==========

Overview
--------
Simple SendGrid client for Java

Dependency
----------
This library requires following jars.

- [JavaMail](http://www.oracle.com/technetwork/java/javamail/index.html)
- http: [AsyncHttpClient](https://github.com/AsyncHttpClient/async-http-client) or [HttpComponents](http://hc.apache.org/)
- json: [Jackson](https://github.com/FasterXML/jackson) or [Google gson](https://code.google.com/p/google-gson/)

JavaDoc
-------
http://oss.flect.co.jp/apidocs/sendgrid4j/index.html

Usage
-----
Send mail

    SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
    WebMail mail = new WebMail();
    //Set from and to information
    mail.setFrom("test@flect.co.jp");
    mail.setTo("test@flect.co.jp");
    mail.setFromName("test");
    mail.setToName("test");
    
    //Subject and text(or You can use setHtml)
    mail.setSubject("Test - " + new Date());
    mail.setText("This is a test mail");
    
    //Category(X-SMTPAPI option)
    mail.setCategory("test");
    
    client.mail(mail);

Get bounces

    SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
    
    Bounce.Get request = new Bounce.Get();
    request.setDays(7);
    List<Bounce> list = client.getBounces(request);
    for (Bounce bounce : list) {
        System.out.println(bounce.getEmail());
    }

Get statistics

    SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
    Statistic.Get request = new Statistic.Get();
    //request.setCategory("test");
    request.setDays(10);
    List<Statistic> list = client.getStatistics(request);
    for (Statistic stat : list) {
        System.out.println("Statistics: " + 
            stat.getDate() + ", " +
            "Category = " + stat.getCategory() + ", " +
            "Requests = " + stat.getRequests() + ", " +
            "Bounces = " + stat.getBounces() + ", " +
            "Clicks = " + stat.getClicks() + ", " +
            "Opens = " + stat.getOpens() + ", " +
            "SpamReports = " + stat.getSpamReports() + ", " +
            "UniqueClicks = " + stat.getUniqueClicks() + ", " +
            "UniqueOpens = " + stat.getUniqueOpens() + ", " +
            "Blocked = " + stat.getBlocked() + ", " +
            "Delivered = " + stat.getDelivered() + ", " +
            "Unsubscribes = " + stat.getUnsubscribes() + ", " +
            "InvalidEmails = " + stat.getInvalidEmails() + ", " +
            "RepeatUnsubscribes = " + stat.getRepeatUnsubscribes() + ", " +
            "SpamDrops = " + stat.getSpamDrops() + ", " +
            "RepeatBounces = " + stat.getRepeatBounces() + ", " +
            "RepeatSpamReports = " + stat.getRepeatSpamReports() + ", " +
            "");
    }

Each class is a simple wrapper of WebAPI parameters or a JSON response.  
Please see the SendGrid WebAPI documentation for details of each property.

http://sendgrid.com/docs/API_Reference/Web_API/index.html

All methods are called from SendGridClient class,  
so you can understand what this can do by seeing [SendGridClient.java](https://github.com/shunjikonishi/sendgrid4j/blob/master/src/main/java/jp/co/flect/sendgrid/SendGridClient.java).

License
-------
MIT

