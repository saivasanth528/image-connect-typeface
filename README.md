# Image-connect


## Requiremennts

https://www.kaggle.com/datasets/adityajn105/flickr8k

Data Setup:
8k images in the zip file
user1 to user100 -> each user is a owner of 80 images.
Also, each user shares these 80 images with 10 other users.
So in total any one user will have 80 images that are owned and 800 images that are shared with him.


App (You can keep it simple, unless you find time to focus on this)
1. Two tabs - Owned images, shared images
    1. When clicked on image name, open image in a popup/different page (with all comments+user who commented it with latest comments on top).
2. Unless shared, one user shouldn't see other users images.
3. User can add comment on either own image/shared image & this should be persisted in DB. Additionally this event should be broadcasted to all users.
   (No need to maintain hierarchy of comments.)


Stretch
1. Like comment - Anyone who can view the comment can like it.
2. Delete comment - Owner of the image can delete a comment. (No notification)
3. Remove sharing, share with permissions (read/write). READ - only view image/comment, WRITE - READ+add comment.

## TechStack Used


- [Angular](https://angular.io/) - For front end
- [Spring boot](https://spring.io/projects/spring-boot) - For backend
- [MySql](https://www.mysql.com/) - My Sql



## Infra Set up Instructions
- Make sure you have installed the following in your local
-  [npm](https://www.youtube.com/watch?v=pHz7TgEIa0w)
- [angular](https://www.youtube.com/watch?v=bGsuJqX_65w)
- [MySQL](https://youtu.be/nj3nBCwZaqI)

## How to set up the system
- Make sure you have installed everything above before coming to here
- Here the application will run on two ports front will be running on **4200** port, backend will be on **8080** port
- Please maintain the same ports to avoid the CORS issue
- In order to run the front end navigate to **ui folder** in the repo and run **ng serve** command
- For backend we have to do small changes, first of all please download the images.zip file from [kaggle link](https://www.kaggle.com/datasets/adityajn105/flickr8k) and paste it under resources directory
- Github has some size restrictions , that is the reason
- Create a database called **image_connect** in your local
- There is one method called **startUpScript** in ImageConnectApplication class which runs after the application starts, it will do the heavy lifting of creating   and generating the data for you
- Please note it after you ran it for the first time, please comment it, such that it wont create the data again
- Similarly there is another method called **deleteAllRecords** in ImageConnectApplication class which destroys all the
  records in mysql tables, please comment it if you don't want to delete the data when your server gets stopped
- After that from the root repository run this command
```sh
mvn spring-boot:run
```
- It will take some time to generate the data


## Testing the system
- Navigate to http://localhost:4200/users/user1/shared to test the system
- For the simplicity i am creating only 11 users in my system, each user owns the 80 images, each user shares 80 images with 10 users , so on owned tab he can see 80 images and 800 images in shared tab, these can be configured in the application.properties
- Why i have done this because of uplaoding the images to s3 takes lot of time. So, i have done it for 11 users only


## My Progress
- I have done everything **excluding the stretch part** since i didn't get the time
- I have tested the owned, shared, comments, notifications , looks good to me
- Please test it from your end, let me know if you face any issues. i will also upload the screenshots and screen recording also if required for validation


## Challenges
- Setting up the environment for front end and backend
- connecting them
- Lot of errors and resolved all of them
- I overlooked the some code quality issues for speed purpose, please don't mind




