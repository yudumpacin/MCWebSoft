# MCWebSoft
MCWebSoft is a tool-chain for verifying web applications and replaying the error scenarios. 

MCWebSoft takes "state machine of web applications" as an input. These state machines can be generated from Web Crawlers. 
Crawljax (State Flow Graph) and Micro-Crawler (Web State Machine) tools can be used for generating state machines for MCWebSoft. MCWebSoft converts these state machines to intermediate web models. To verify the intermediate web model,  model-checker, NuSMV 2.5.4 is embedded to MCWebSoft. Model checker checks CTL properties, which verify navigation and access control of web applications. At the end of this process, counterexamples are generated. MCWebSoft converts these counterexamples to Selenium scripts and automatically replay them on web browser to demostrate errors.
