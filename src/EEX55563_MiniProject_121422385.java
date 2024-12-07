import java.util.Scanner;
public class EEX55563_MiniProject_121422385 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Input the total memory size
        System.out.print("Enter the total memory size before partitioning: ");
        int totalMemory = scanner.nextInt();

        // Step 2: Input the number of memory blocks and distribute memory
        System.out.print("Enter the number of memory blocks: ");
        int numBlocks = scanner.nextInt();
        int[] memoryBlocks = new int[numBlocks];
        boolean[] isAllocated = new boolean[numBlocks]; // Track allocation status

        System.out.println("Enter the size of each memory block (total must not exceed " + totalMemory + "):");
        int totalAllocated = 0;
        for (int i = 0; i < numBlocks; i++) {
            memoryBlocks[i] = scanner.nextInt();
            totalAllocated += memoryBlocks[i];
            if (totalAllocated > totalMemory) {
                System.out.println("Error: Total partition sizes exceed available memory.");
                return; // Exit if partitions exceed total memory
            }
            isAllocated[i] = false; // Initialize all blocks as free
        }

        // Step 3: Input the number of processes and validate
        int numProcesses = 0;
        while (numProcesses <= 0) {
            System.out.print("Enter the number of processes (positive integer): ");
            if (scanner.hasNextInt()) {
                numProcesses = scanner.nextInt();
                if (numProcesses <= 0) {
                    System.out.println("Error: Number of processes must be a positive integer.");
                }
            } else {
                System.out.println("Error: Invalid input. Please enter a positive integer.");
                scanner.next(); // Clear the invalid input
            }
        }

        // Initialize process sizes array
        int[] processSizes = new int[numProcesses];

        // Input each process size with validation
        System.out.println("Enter the size of each process:");
        for (int i = 0; i < numProcesses; i++) {
            int size = -1;
            while (size <= 0) {
                System.out.print("Process " + (i + 1) + " size: ");
                if (scanner.hasNextInt()) {
                    size = scanner.nextInt();
                    if (size <= 0) {
                        System.out.println("Error: Process size must be a positive integer.");
                    }
                } else {
                    System.out.println("Error: Invalid input. Please enter a positive integer.");
                    scanner.next(); // Clear the invalid input
                }
            }
            processSizes[i] = size;
        }

        // Step 4: Initialize variables for allocation
        int[] allocation = new int[numProcesses]; // To store allocation results
        for (int i = 0; i < numProcesses; i++) {
            allocation[i] = -1; // Initialize all allocations as not allocated
        }
        int lastAllocatedIndex = 0; // Start from the first block

        // Step 5: Start allocation for each process
        for (int i = 0; i < numProcesses; i++) {
            boolean allocated = false;

            // Search from the last allocated index
            for (int j = 0; j < numBlocks; j++) {
                int currentIndex = (lastAllocatedIndex + j) % numBlocks; // Circular search

                // Check if the current block can accommodate the process
                if (!isAllocated[currentIndex] && memoryBlocks[currentIndex] >= processSizes[i]) {
                    allocation[i] = currentIndex; // Allocate process to the block
                    memoryBlocks[currentIndex] -= processSizes[i]; // Reduce block size
                    isAllocated[currentIndex] = true; // Mark block as allocated
                    lastAllocatedIndex = currentIndex; // Update last allocated index
                    allocated = true;
                    break; // Move to the next process
                }
            }

            // If no suitable block found, leave allocation as -1
            if (!allocated) {
                allocation[i] = -1; // Process not allocated
            }
        }

        // Step 6: Output the allocation results
        System.out.println("\nProcess Allocation Results:");
        for (int i = 0; i < numProcesses; i++) {
            if (allocation[i] != -1) {
                System.out.println("Process " + (i + 1) + " of size " + processSizes[i]
                        + " is allocated to block " + (allocation[i] + 1));
            } else {
                System.out.println("Process " + (i + 1) + " of size " + processSizes[i]
                        + " could not be allocated.");
            }
        }

        // Step 7: Output the remaining sizes of the memory blocks
        System.out.println("\nRemaining Memory in Each Block:");
        for (int i = 0; i < numBlocks; i++) {
            System.out.println("Block " + (i + 1) + ": " + memoryBlocks[i]);
        }

        scanner.close();
    }
}