# Tributary Event Streaming Library

A lightweight **event-streaming system written in Java** that simulates
the core concepts behind distributed messaging platforms like Apache
Kafka.

This project implements an event-driven messaging pipeline where
**producers publish events** and **consumers process them asynchronously
through partitioned streams**.

------------------------------------------------------------------------

## Overview

Modern distributed systems rely heavily on **event streaming** to
decouple services and enable scalable asynchronous communication.

This library provides a simplified event streaming platform where:

-   Producers generate events
-   Events are appended to **topics**
-   Topics are divided into **partitions**
-   Consumers process events from partitions
-   Consumer groups enable **parallel processing**

------------------------------------------------------------------------

## Architecture

### Cluster

The central event streaming system containing all topics.

### Topics

Logical categories of events.

Example:

    user-updates
    order-events
    notifications

### Partitions

Each topic is split into partitions to allow parallel event processing.

    Topic
     ├── Partition 0
     ├── Partition 1
     └── Partition 2

Events are appended to partitions in order.

------------------------------------------------------------------------

### Producers

Producers publish events into topics.

Two allocation strategies are supported:

-   **Random Producer** -- events assigned randomly to partitions\
-   **Manual Producer** -- events assigned using a partition key

------------------------------------------------------------------------

### Consumers

Consumers read events sequentially from partitions.

A **consumer group** distributes partitions among consumers so
processing can occur in parallel.

------------------------------------------------------------------------

### Rebalancing Strategies

When consumers join or leave a group, partitions are redistributed
using:

**Range Rebalancing**

    Consumer A → partitions 0,1,2
    Consumer B → partitions 3,4

**Round Robin Rebalancing**

    Consumer A → partitions 0,2,4
    Consumer B → partitions 1,3

------------------------------------------------------------------------

### Event Replay

Consumers can replay messages from a given **offset**, enabling:

-   debugging
-   rebuilding state
-   reprocessing historical events

------------------------------------------------------------------------

## Features

-   Event streaming architecture
-   Topic and partition system
-   Producer allocation strategies
-   Consumer groups with partition assignment
-   Range and round-robin rebalancing
-   Event replay from offsets
-   Thread-safe event handling
-   Command-line interface

------------------------------------------------------------------------

## Example Event Flow

    Producer → Topic → Partition → Consumer Group → Consumer

1.  Producer sends an event
2.  Event is appended to a partition
3.  Consumer group distributes partitions
4.  Consumer processes the event

------------------------------------------------------------------------

## CLI Example

    create topic orders String
    create partition orders 0
    create producer producer1 String Random

    produce event producer1 orders order.json

    create consumer group group1 orders Range
    create consumer group1 consumer1

    consume event consumer1 0

------------------------------------------------------------------------

## Project Structure

    src
     ├── api
     │    Public API used by developers
     │
     ├── core
     │    Internal implementation
     │
     └── cli
          Command line interface

------------------------------------------------------------------------

## Technologies

-   Java
-   Object-oriented design
-   Concurrency and synchronization
-   Event-driven architecture

------------------------------------------------------------------------

## Purpose

This project explores how **event streaming platforms work internally**,
demonstrating many concepts used in systems like Apache Kafka in a
simplified implementation.

------------------------------------------------------------------------

## Future Improvements

Potential improvements include:

-   distributed node support
-   persistent storage
-   networked producers and consumers
-   monitoring dashboard

------------------------------------------------------------------------

## License

Provided for educational and demonstration purposes.
