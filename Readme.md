URL shortener - Spring boot + MongoDB + RESTFul APIs

Features:
1. Shortener url using Base62 encoder, returns short code
2. Redirect to url using short code 

Set up:
git clone https://github.com/shleshathaker5/URL-shortening-MongoDB.git
cd URL-shortening-MongoDB
Download mongo db, or use cloud version, use appropriate url in application.yml e.g mongodb://localhost:27017/urlshortener


Steps to run:
1. set up local env
2. Build and run spring boot app (http://localhost:8080 running port)
3. Start Mongodb
   4. Sample req 
          Create Short URL
          POST /api/shorten
          Request
          {
          "longUrl": "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
          }
          Response
          {
          "shortCode": "gLizp3styklLeoSn",
          "shortUrl": "http://localhost:8080/gLizp3styklLeoSn"
          }