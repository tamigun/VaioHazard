package org.dimigo.vaiohazard.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import org.dimigo.library.Rand;
import org.dimigo.vaiohazard.Device.Components;
import org.dimigo.vaiohazard.Device.Vaio;
import org.dimigo.vaiohazard.Device.VaioProblem;
import org.dimigo.vaiohazard.control.Scheduler;
import org.dimigo.vaiohazard.conversation.Conversation;

import java.time.DayOfWeek;
import java.util.*;

/**
 * Created by YuTack on 2015-11-11.
 * But I made it!
 */

public class ServiceCenter {
    private static ServiceCenter center = new ServiceCenter();
    public static ServiceCenter getInstance() {
        return center;
    }

    public static int[] monthlyDate = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};

    private ServiceCenter() {
        money = 10000;
        reputaionPercent = 30 / 100;
        year = 2015;
        month = 1;
        day= 1;
        minutes = 540;
        dayOfWeek = DayOfWeek.MONDAY;
        customers = new ArrayList<Customer>();
    }

    private int money;
    private float reputaionPercent;
    private List<RepairOrder> orders = new ArrayList<RepairOrder>();

    private int timerTick;
    private int year;
    private int month;
    private int day;
    private int minutes;

    public Stage getCurrentStage() {
        return currentStage;
    }

    private DayOfWeek dayOfWeek;

    //0 에서 1사이의 값
    private float inspectSkill = 0.5f;

    private Stage currentStage;
    //repairOrders에서 오늘날짜 고객들을 서치해서 customers에 넣어 줌.
    private Scheduler todaySchedule;

    //지금 가게에 렌더링되고 있는 고객만 있음
    private ArrayList<Customer> customers;
    private int waitingNumber = 0;
    private int countNumber = 1;
    public static final int MAX_COUNTER_NUM = 1;
    public static final int MAX_WAITING_NUM = 5;

    private Components components;



    /*public static ServiceCenter loadCenter() {

    }*/

    private void timeFlow() {
        timerTick++;
        if(timerTick %30==0) minutes++;
        if(minutes > 1080) {
            timerTick = 0;
            minutes = 540;
            day++;
        }
        if(day > monthlyDate[month-1]) {
            day = 1;
            month++;
        }
        if(month > 12) {
            month = 1;
            year++;
        }
    }

    public void updateDate() {
        todaySchedule = new Scheduler(month, day, orders);
    }

    /*public void updateWaitingState(Customer orderer) {
        customers.remove(orderer);
        for(Customer customer : customers) {
            if(customer.getCustomerState() == Customer.CustomerState.waitForTurn) {
                //대기번호 하나씩 줄어듬, 대기번호 0번되면 자동으로 카운터로 이동하고 협상 대기 상태가 됨
                customer.updateWaitingNumber();
            }
        }
    }*/

    public void update(float deltaTime) {
        timeFlow();

        if(todaySchedule != null) {
            todaySchedule.update(deltaTime);
        }

        for(Customer customer : customers) {

            Customer.CustomerState state = customer.getCustomerState();

            if(state == Customer.CustomerState.readyToNegotiate) {
                (new Conversation(currentStage, customer)).start();
                customer.notifyConversationStarted();
            } else if(state == Customer.CustomerState.waitForTurn) {
            }
        }

    }

    static public class InspectResult {
        public Map<VaioProblem.Trouble, VaioProblem.Critical> impairs;
        public int failCount;
    }

    //Fine인 문제를 착각할 정도로 멍청하진 않음, 그러나 스킬에 따라 있는 문제를 모르거나(가벼운 경우만) 심각성을 잘못 판단할 수 있음
    public InspectResult inspectVaio(Vaio vaio) {
        Map<VaioProblem.Trouble, VaioProblem.Critical> inspectResult = new HashMap<VaioProblem.Trouble, VaioProblem.Critical>();
        int failCount = 0;

        for (VaioProblem.Trouble realTrouble : vaio.getImpairs().keySet()) {
            if (vaio.getImpairs().get(realTrouble) == VaioProblem.Critical.Fine) {

                inspectResult.put(realTrouble, VaioProblem.Critical.Fine);

            } else if (vaio.getImpairs().get(realTrouble) == VaioProblem.Critical.Little) {

                if (Rand.get(inspectSkill)) inspectResult.put(realTrouble, VaioProblem.Critical.Little);
                else {
                    inspectResult.put(realTrouble, VaioProblem.Critical.Fine);
                    failCount++;
                }

            } else {

                if (Rand.get(inspectSkill)) {
                    inspectResult.put(realTrouble, vaio.getImpairs().get(realTrouble));
                } else {
                    Random rand = new Random();
                    inspectResult.put(realTrouble, VaioProblem.Critical.values()[2 + rand.nextInt(3)]);
                    failCount++;
                }

            }
        }

        InspectResult result = new InspectResult();
        result.impairs = inspectResult;
        result.failCount = failCount;
        return result;
    }

    public void addRepairOrder(RepairOrder order) {
        //updateWaitingState(order.getOrderer());
        orders.add(order);
        ++countNumber;
    }

    //화면에 실제 렌더링 되는 고객을 추가하는것, 주문서에 예약된 손님이 다시올 수도 있고 새손님일 수도 있음
    public void addCustomer(Customer customer) {
        currentStage.addActor(customer);
        customers.add(customer);
        customer.setWaitingNumber(++waitingNumber);
    }

    public List<RepairOrder> getOrders() {
        return orders;
    }

    public int getMoney() {
        return money;
    }

    public float getReputaionPercent() {
        return reputaionPercent;
    }

    public float getInspectSkill() { return inspectSkill; }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public int getMinutes(){
        return minutes;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setCurrentStage(Stage stage) { this.currentStage = stage; }

    public int getWaitingNumber() { return waitingNumber; }
    public int getCountNumber() { return countNumber; }
}