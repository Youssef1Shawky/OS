# Simulated Operating System Project

## Overview

This project simulates an operating system by implementing a basic interpreter for text-based programs. The interpreter reads program files, converts them into processes, manages memory allocation, implements mutexes for resource control, and utilizes a round-robin scheduler for process execution.

### Objective

The main objective of this project is to build a correct architecture simulating real operating systems. The implementation should handle system calls, memory management, process control blocks (PCB), mutexes for mutual exclusion, and a round-robin scheduler.

## Components Implemented

### Code Parser/Interpreter

- Reads text files representing programs and executes their code.
- Interprets instructions such as print, assign, writeFile, readFile, printFromTo, semWait, and semSignal.

### System Calls

- Handles requests for services from processes, including file reading, writing text output, printing data on the screen, taking user input, reading/writing data from/to memory.

### Mutexes

- Implements mutexes for controlling access to shared resources: file access (read/write), user input, and screen output.
- Uses semWait and semSignal instructions to ensure mutual exclusion over critical sections.

### Scheduler

- Implements the Round Robin scheduling algorithm.
- Assigns a fixed time slice where each process executes 2 instructions per time slice.
- Manages Ready Queue and Blocked Queue for process scheduling and resource availability.

### Memory Management

- Allocates space in the main memory for processes.
- Handles memory boundaries and manages process data in memory.
- Swaps process data to/from disk when memory is insufficient and manages memory on disk.

## Programs Included

### Program 1

- Given 2 numbers, prints the numbers between the given range on the screen.

### Program 2

- Writes provided data to a specified file.

### Program 3

- Prints the contents of a specified file on the screen.

## Outputs

For evaluation purposes, the following outputs are provided:

- Queues printed after every scheduling event.
- Current executing process and instruction.
- Human-readable memory representation at every clock cycle.
- IDs of processes swapped in or out of disk.
- Format of memory stored on Disk.

## Work Distribution

- Code Parser/Interpreter
- System Calls
- Mutexes
- Scheduler
- Memory Management

## Usage

Provide instructions on how to run the simulated OS, including any setup required, command-line instructions, and expected outputs.
