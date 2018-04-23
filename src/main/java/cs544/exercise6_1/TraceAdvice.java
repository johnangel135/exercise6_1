package cs544.exercise6_1;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.lang.*;



/**
 * Created by Duong Truong on 4/22/2018.
 */
@Aspect
public class TraceAdvice {
    private static Logger logger = Logger.getLogger(App.class);
    @After("execution(* EmailSender.sendEmail(..))")
    public void afterEmail(JoinPoint joinPoint){
        System.out.println(Calendar.getInstance().getTime() + " method= " + joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        System.out.println("message= " + args[1]);
        EmailSender emailSender = (EmailSender) joinPoint.getTarget();
        System.out.println("outgoing mail sever: " + emailSender.outgoingMailServer);
    }

    @Around("execution(* CustomerDAO.save(..))")
    public Object invoke(ProceedingJoinPoint call ) throws Throwable{
        StopWatch sw = new StopWatch();
        sw.start(call.getSignature().getName());
        Object retVal = call.proceed();
        sw.stop();
        long totaltime = sw.getLastTaskTimeMillis();
        // print the time to the console
        System.out.println("Time to execute save= " + totaltime + "ms");
        return retVal;
    }
}
