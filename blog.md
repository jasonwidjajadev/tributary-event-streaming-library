# 5.1.1. Task 1) Preliminary Design (5 marks)

### Before you begin work you will need to complete a preliminary, high-level design up front. In a blog post, write an engineering specification which includes:

> An analysis of the engineering requirements;

Answer: We have planned the following as our draft working director:

tributary
├── api
│   ├── API.java
│   └── APIFactory.java
├── cli
│   └── TributaryCLI.java
│   └── events
│       └── temperature_update_1.json
└── core
    ├── AdminClient.java
    ├── clients
    │   ├── admin
    │   │   ├── Broker.java
    │   │   ├── ConsumerCoordinator.java
    │   │   └── ProducerCoordinator.java
    │   ├── consumer
    │   │   ├── Consumer.java
    │   │   ├── ConsumerGroups.java
    │   │   ├── ConsumerRecord.java
    │   │   └── internals
    │   │       ├── RangeRebalancing.java
    │   │       ├── RebalancingStrategy.java
    │   │       └── RoundRobinRebalancing.java
    │   └── producer
    │       ├── Producer.java
    │       └── ProducerRecord.java
    └── common
        ├── Headers.java
        ├── Partition.java
        └── Topic.java

> A list of usability tests - a “checklist” of scenarios which someone testing your system via the command line interface could use to verify that your system works according to the requirements.

Answer:

> An initial UML diagram showing the entities, relationships between entities and key methods/fields in each entity (does not have to be a complete list, it just needs to be a first-version API)

Answer:

> Your design for a Java API by which someone could use your solution to setup an event-driven system.
 A testing plan which explains how you are going to structure your tests for the system. You will need a mix of unit tests on individual components as well as integration tests to check that components work together. Your usability tests will need to be incorporated into this plan as well.

Answer:

### When it comes to implementation, there are two ways you can go about implementing a solution:

> Feature-driven - essentially, working through the table in Section 3.2 and getting one section working at a time. This approach is easier to usability test incrementally, harder to incorporate with multiple people, and can result in less cohesive software design and test design (more of an Incremental Design Approach)

> Component-driven - creating each component and writing unit tests individually, before bringing the pieces together and usability testing at the end. This approach is harder to usability test incrementally, easier to incorporate with multiple people but can result in more cohesive software and test design (more of a Big Design Up Front Approach)

Answer:

> You will need to pick one approach and justify your decision. All of this should occur in a blog post before you start coding. You’ll be able to iterate on the design as you go, this is just the preliminary design.No marks will be awarded for a preliminary design with no implementation.

### Further iteration notes

Answer:

### 5 mins video
> The purpose of the video is to walk through each scenario on your usability test "checklist" using your CLI - you do not need to walk through the code, nor show JUnit tests running;
Your video should be 5 minute or less. Videos longer than 5 minutes won’t be marked;
Any CLI text/output should be clearly legible - do not upload a low resolution video;
Upload your video to Google Drive or YouTube as an unlisted video (it must be publicly viewable) and link in your blog to submit.


To make your video demonstration clearer, you can:

- Index the video for specific features;
Write out all the commands you plan to use beforehand, and copy them into the CLI to be run.
- The breakdown of marks in this section is as follows.
- 8 marks pair, The system functions with a single producer, a single topic, a single partition, a single consumer group and a single consumer such that a message lifecycle can be completed. Events only have to be of a single type for a pass.
- 12 marks pair, All requirements for a pass, as well as support for multiple types of events and demonstration of producers and consumers working in parallel.
- Distinction (16 marks pair, 20 marks solo): All requirements for a credit, as well as implementation of consumer groups with no rebalancing, implementation of producer message allocation options.
- High Distinction (20 marks pair), All requirements for a credit, as well as a full consumer rebalancing implementation and support for replaying of events.


### 5.2.1. Task 3) Final Design (15 marks)

In a second blog post you will need to write in essence a report on your final solution. Your blog post will need to contain:

- Your final testing plan and your final list of usability tests;
- An overview of the Design Patterns used in your solution;
- Explanation of how you accommodated for the design considerations in your solution;
- Your final UML diagram; and
- A brief reflection on the assignment, including on the challenges you faced and whether you changed your development approach. We will use this blog post and your code to assess the overall design of your solution.