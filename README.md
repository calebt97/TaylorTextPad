# TaylorTextPad
A basic text editor that I'll improve on, hopefully an IDE one day. Written purely in java
If you care to help me improve it, just email me. Any comments or suggestions would be really helpful

1.0: Allowed opening, closing and saving files within a JTextPane. Can change font size and style.

2.0: Allows specification of save/opening location using JFileDirectory. If file is saved as .java, it can run the code. 
However, the output is put in the console of IDE the developer is using. Improved the GUI, using JMenuItems and other tools.
Windowlistener gives the option of saving your work before the system exits.

2.1: A few bug fixes, and transferred GUI constructor to various static methods to increase readability.

2.2: A few more bug fixes, and comments for readability

3.0: Added a console for output from the code written in the textpad. The console was built using JTextPane. The code output is appended to the console. More bug fixes.

3.1: Minor fixes to the OutputConsole.java and CompileAndRun.java files. Save method in the Main.java was producing a copy, not overwriting the file. So I fixed that using a static File object. 

3.2: Minor bug fixes. File autosaves without asking user every 25 keys typed. File autosaves when asked to run, assuming it has already been "save as"ed or opened from the file directory.

3.21: More comments/explanations. Changed constructor for FontBuilder class.

3.22(Final Version):Ran through linter and applied suggested changes. Finalized documentation.
