# jagi
JAGI is a virtual "fork" of Dr Zoltan's Java AGI engine that can be found on sourceforge here:

https://sourceforge.net/projects/agi/

The initial commit of the code to this github repo was taken from the above sourceforge CVS repo on the 29th October 2016. The code in that repo had not changed in many years, so in the most part this initial version of the code dates back to 2001-2002. Given that date range, it is likely that it was originally built under Java 1.3. As a result, there were a number of classes that didn't compile under Java 8, mainly due to the use of "enum" for variable and method names in a few places. So the second commit (the first to diverge from the original project's code base), changed these variable names to allow it to compile under Java 8.

With this repo now set-up, I am intending from this point onward to use this existing code base as way to explore some of the earlier AGI v1 booter style games, on both the PC and Apple II platforms. I'm hoping that I can make use of the various interfaces that the Java AGI interpreter provides, and it's existing capability to plug in different implementations of those interfaces, to enable the debugging tools it has for AGI v1, such as the various resource viewers and the LOGIC decompiler. It will start out mainly as an investigative phase at first.

If you are Dr Zoltan and are currently reading this, then I'd love to talk to you about how we might collaborate on the original project to drive it forward. Please get in touch.
