Task Manager and Timer
====

## Contributors

- Xiangji An
- Geedi Bryden
- Michael Harewood
- Ying Xu

## Summary

This program enables users to create and manage timed tasks effectively.
Also connects with calendar API to create events.
Helps boost productivity and work efficiency.


![image of branch protection rules for main with the
requirement of two approvers to merge in pull requests.
](images/branch_protection_rules.png)

## Table of Contents

1. [Project Blueprint](#project-blueprint-demo)
2. [Features of the Project](#features-of-the-software)
3. [Installation Instructions](#installation-instructions)
4. [Usage Guide](#usage-guide)
5. [License](#license)
6. [Feedback](#feedback)

## Project Blueprint DEMO

Domain: Focus & Flow; Timed Task Setter
Software Specification:
This program enables users to create and manage timed tasks effectively.
Users can define specific tasks along with the time they estimate is needed to complete each one.
The software tracks these timers and tasks, allowing users to monitor their progress.
The user can pause a task if they need to.
If a user decides to pause or cancel a task, it will be marked as unsuccessful.
An unsuccessful task can either be restarted or deleted.
Conversely, successfully completed tasks will be labeled as successful and recorded in the user’s profile,
which maintains a comprehensive history of both completed and uncompleted (paused and cancelled) tasks.
Users will also have the ability to view their completed and uncompleted tasks,
allowing them to correct any errors if necessary.

User Stories:
1. Lia needs to manage her time effectively for her group project. She starts the Flow & Focus software and sets a timer labeled ‘Group Project Work’ for an hour and a half. [team story]
2. Lia is in the middle of her “Reading Time” timer when she receives a phone call. She pauses her timer to take the call and resumes her reading afterward.
3. Lia realizes she needs to review her productivity. She opens the Flow & Focus software to access her task history, where she can view both completed and uncompleted tasks.
4. Lia needs to correct an error in her task history. She reviews her completed tasks and edits one that was incorrectly labeled, ensuring her records are accurate. [team story]
   Proposed Entities for the Domain: User
- String username
- String password Task
- String title
- int estimatedDuration (in minutes)
- String status (e.g., Pending, Completed, Paused)
  Record
- List<Task> tasks

- String completionStatus (e.g., Successful, Unsuccessful, Paused) Focus Report
- String username
- int totalTasksCompleted
- int totalFocusTime
  Scheduled Meeting Times + Mode of Communication: Meeting time outside of lab: Flexible, twice a week
  Mode of Communication: WhatsApp, Zoom

## Features of the Software

This program enables users to create and manage timed tasks effectively.
Users can define specific tasks along with the time they estimate is needed to complete each one.
The software tracks these timers and tasks, allowing users to monitor their progress.
The user can pause a task if they need to.
If a user decides to pause or cancel a task, it will be marked as unsuccessful.
An unsuccessful task can either be restarted or deleted.
Conversely, successfully completed tasks will be labeled as successful and recorded in the user’s profile,
which maintains a comprehensive history of both completed and uncompleted (paused and cancelled) tasks.
Users will also have the ability to view their completed and uncompleted tasks,
allowing them to correct any errors if necessary.

## Installation Instructions

1. Ensure you have the 22nd edition of Java installed on your device.
2. Clone or download the app from the [repository](https://github.com/Zephyrus100/Project).
3. Then Run the [Main file](src/main/java/app/Main.java)
4. The various functions can be accessed by the buttons on the home page.

## Usage Guide

1. The 'Enter Task' button allows new tasks to be created by setting its time, name and description.
2. The 'Current Tasks' button will allow you to see all previously created tasks.
3. The 'Timer' button will allow you to use the timer with start, pause, resume, stop and reset functions.
4. The 'Report' button allows you to see previous tasks and see how long you've been focused.

## License

License for this project is the Creative Commons Legal Code.
This means it is free for public use without restrictions.

## Feedback

We accept feedback to the emails listed in our GitHub accounts.
Feedback will only be considered if it is constructive and helpful!

