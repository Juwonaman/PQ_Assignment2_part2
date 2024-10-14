// Name: <Olajuwon Atunnise>
// Class: CS 3305/Section # CS 3305/W02
// Term: Summer 2024
// Instructor: Dr. Bobbie
// Assignment: Part 2
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.NoSuchElementException;
public class ProcessManager <T extends Comparable<T>> implements Comparable<ProcessManager<T>> {
    private T ProcessID;
    private int  PriorityNumber;
    private int  ProcessWTime;
    private int  ProcessCompletionP;
    private double ProcessATime;
    private double ProcessCompletionTime;
    private double tis;
    private double ProcessSTime;
    private int QueueSize;
    public double getTis() {
        return tis;
    }

    public void setTis(double tis) {
        this.tis = tis;
    }
    public void setProcessSTime(double processSTime) {
        ProcessSTime = processSTime;
    }
    public int getProcessWTime() {
        return ProcessWTime;
    }
    public double getProcessATime() {
        return ProcessATime;
    }
    public void setProcessCompletionP(int processCompletionP) {
        ProcessCompletionP = processCompletionP;
    }
    public double getProcessSTime() {
        return ProcessSTime;
    }
    public void setProcessWTime(int processWTime) {
        ProcessWTime = processWTime;
    }
    public ProcessManager(T PI, int PN, int PWT, double PAT, double PST) {
        this.ProcessID = PI;
        this.PriorityNumber = PN;
        this.ProcessATime = PAT;
        this.ProcessSTime = PST;
        this.QueueSize = 0;
        this.ProcessWTime = PWT;
        this.ProcessCompletionP = 1;
    }
    @Override
    public int compareTo(ProcessManager other) {
        return Integer.compare(this.PriorityNumber,other.PriorityNumber);
    }
    public String toString() {
        return "Process ID: " + ProcessID + ", Priority: " + PriorityNumber+ ", Arrival Time: " + (long)ProcessATime+ ", Service Time: " + (long)ProcessSTime + ", Waiting Time: " + ProcessWTime + ", Time in System: " + tis +  ", Completion Order: " + ProcessCompletionP +"\n";
    }
    public double getProcessCompletionTime() {
        return ProcessCompletionTime;
    }
    public void setProcessCompletionTime(long currentTimeMillis) {
        ProcessCompletionTime = currentTimeMillis;
    }
}
class MaxHeap<T extends Comparable<T>>{
    private int compOrd =1;
    private int count = 0;
    private ArrayList<T> heap;
    private long totalWaitingTime = 0;
    private long totalServiceTime = 0;
    private long totalTimeInSystem = 0;
    public MaxHeap(){
        heap = new ArrayList<>();
    }
    public void insert(T process){
        heap.add(process);
        heapifyUP(heap.size()-1);
    }
    public T remove() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        } else {
            T root = heap.get(0);
            T last = heap.remove(heap.size() - 1);
            if (!heap.isEmpty()) {
                heap.set(0, last);
                heapifyDown(0);
            }
            Waiting_time(root);
            return root;
        }
    }
    private void heapifyUP(int i){
        while(i > 0){
            int parentI = (i -1)/2;
            T current = heap.get(i);
            T parent = heap.get(parentI);
            if(current.compareTo(parent) > 0){
                swap(i, parentI);
                i = parentI;
            }
            else {
                break;
            }
        }
    }
    private void heapifyDown (int i) {
        int lastI = heap.size() - 1;
        while (i < lastI) {
            int leftChildI = 2 * i + 1;
            int rightChildI = 2 * i + 2;
            int largestI = i;
            if (leftChildI <= lastI && heap.get(leftChildI).compareTo(heap.get(largestI)) > 0) {
                largestI = leftChildI;
            }
            if (rightChildI <= lastI && heap.get(rightChildI).compareTo(heap.get(largestI)) > 0) {
                largestI = rightChildI;
            }
            if (largestI != i) {
                swap(i, largestI);
                i = largestI;
            } else {
                break;
            }
        }
    }
    private void swap(int index1, int index2) {
        T temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MaxHeap:\n");
        if (!heap.isEmpty()) {
            sb.append(heap.get(0)).append("\n");
            for (int i = 0; i < heap.size(); i++) {
                if (2 * i + 1 < heap.size()) {
                    sb.append("parent node : ").append(heap.get(i)).append(" left child : ").append(heap.get(2 * i + 1));
                    if (2 * i + 2 < heap.size()) {
                        sb.append(" right child : ").append(heap.get(2 * i + 2));
                    }
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }
    public void Waiting_time(T process) {
        ProcessManager<T> root = (ProcessManager<T>) process;
        root.setProcessSTime(System.currentTimeMillis());
        root.setProcessWTime((int) (root.getProcessSTime() - root.getProcessATime()));
        root.setProcessCompletionP(compOrd++);
        root.setProcessCompletionTime(System.currentTimeMillis());
        Time_In_System(process);

        totalWaitingTime += root.getProcessWTime();
        totalServiceTime += root.getProcessSTime();
        totalTimeInSystem += root.getTis();
        count++;
    }
    public void Time_In_System(T process) {
        ProcessManager<T> root = (ProcessManager<T>) process;
       root.setTis(root.getProcessCompletionTime() - root.getProcessATime());
    }
    public void CPU_Running(){
        remove();
    }
    public void Statistician() {
        DecimalFormat df = new DecimalFormat("#");
        if (count == 0) {
            System.out.println("No processes to compute statistics.");
            return;
        }
        double averageWaitingTime = (double) totalWaitingTime / count;
        double averageServiceTime = (double) totalServiceTime / count;
        double averageTimeInSystem = (double) totalTimeInSystem / count;
        System.out.println("Average Waiting Time: " + averageWaitingTime);
        System.out.println("Average Service Time: " + df.format(averageServiceTime));
        System.out.println("Average Time in System: " + averageTimeInSystem);
    }
}