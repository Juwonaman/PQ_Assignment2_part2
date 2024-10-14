// Name: <Olajuwon Atunnise>
// Class: CS 3305/Section # CS 3305/W02
// Term: Summer 2024
// Instructor: Dr. Bobbie
// Assignment: Part 2
import java.util.Random;
public class Main {
    public static void main(String[] args) {
        MaxHeap<ProcessManager<Integer>> maxHeap = new MaxHeap<>();
        Random rand = new Random();
        for(int i =1; i<51; i++){
            long arrivalTime;
            arrivalTime=System.currentTimeMillis();
            int PN = rand.nextInt(1,99);
            maxHeap.insert(new ProcessManager<>(i, PN,0, arrivalTime,0));
        }
        System.out.println("Max Heap: "+ "\n"+ maxHeap);
        while(!maxHeap.isEmpty()){
            if(maxHeap.isEmpty()){
                System.out.println("Empty");
            }
            ProcessManager<Integer> max = maxHeap.remove();
            System.out.println("Removed Root: " + max);
        }
        maxHeap.Statistician();
    }
}
