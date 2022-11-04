

Admin log in credentials:
username: admin
Password: 543

Link to Repo: https://github.com/LD-UO/Project-SEG-2105

Complaint Test Cases:


Test case 1:
chefUsername: 321
Password: 321

Complaint2: Since the complaint hasn't been addressed, it will show up in the "Show complaints" page when logged in as admin. The chef would be able to log in successfully.

Test case 2:
chefUsername: meekyle
Password: meekyle

Complaint3: Since the complaint has been addressed and the endDate has passed, the chef will be able to log in successfully. This complaint will not show up in the "Show complaints" page.

Test case 3:
chefUsername: Luke
Password: Luke

Complaint4: Since the complaint has been addressed and the endDate has not passed, the chef will not be able to log in. A message is displayed once they attempt to log in saying they have been suspended until yyyy-MM-dd. This complaint will not show up in the "Show complaints" page.

Test case 4:
chefUsername: 3214
Password: 321

ComplaintInfo: Since the complaint has been addressed and the endDate doesn't exist, the chef will not be able to log in ever, as they have been banned indefinitely. A message is displayed once they attempt to log in saying they have been banned indefinitely. This complaint will not show up in the "Show complaints" page.