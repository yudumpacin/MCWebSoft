# MCWebSoft
MCWebSoft is a tool-chain for verifying web applications with model checker and replaying the error scenarios. 
MCWebSoft takes input as state machine of web applications, intermediate web model. These state machines are automatically converted from
output of web crawlers. Currenty output of Crawljax (State Flow Graph) and Micro-Crawler (Web State Machine) can be used as input for MCWebSoft.

NuSMV 2.5.4 is embedded in MCWebSoft as the model checker. CTL properties verifying navigation and access control of web applications are
generated by means of MCWebSoft. Finally, Counterexamples detected by model checker are converted to Selenium scripts which will be replayed on web browser automatically.
