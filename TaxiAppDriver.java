import java.io.*;
import java.util.*;

class NoServiceAvailableException extends Exception
{
String msg;

NoServiceAvailableException()
{
super();
}

NoServiceAvailableException(String msg)
{
super(msg);
this.msg=msg;
}

public String toString()
{
return "NoServiceAvailableException"+msg;
}
}

class Taxi
{
int taxiNo,bkId,cId;
char from='A',to='A';
int pTime;
int dTime;
double amount,totEarnings;

Taxi()
{
}

Taxi(int taxiNo)
{
this.taxiNo=taxiNo;
}

Taxi(int taxiNo,int bkId,int cId,char from,char to,int pTime,int dTime,double amount,double totEarnings)
{
this.taxiNo=taxiNo;
this.bkId=bkId;
this.cId=cId;
this.from=from;
this.to=to;
this.pTime=pTime;
this.dTime=dTime;
this.amount=amount;
this.totEarnings=totEarnings;
}

Taxi(Taxi t)
{
taxiNo=t.taxiNo;
bkId=t.bkId;
cId=t.cId;
from=t.from;
to=t.to;
pTime=t.pTime;
dTime=t.dTime;
amount=t.amount;
totEarnings=t.totEarnings;
}

public String toString()
{
return bkId+"\t"+cId+"\t"+from+"\t"+to+"\t"+pTime+"\t"+dTime+"\t"+amount;
}

}

class TaxiApp
{
java.util.List<Taxi> list;
List<Taxi> trips=new ArrayList<>();
Taxi at;
char fPoint,tPoint;
int pTime;
static int bkId=6700;
static int cId=2300;

TaxiApp(int n)
{
list=new ArrayList<>(n);
Taxi t[]=new Taxi[n];
for(int i=0;i<n;i++)
{
t[i]=new Taxi(i+1);
list.add(t[i]);
}
}

boolean check(char pt)
{
if(pt >= 'G')
return false;
return true;
}

void findTaxi(List<Taxi> list)throws Exception
{
List<Taxi> avail=new ArrayList<>();

for(Taxi k:list)
if(k.to == fPoint && k.dTime <= pTime)
avail.add(k);

if(avail.size()!=0)
at=new Taxi(findLowEarn(avail));

else
{
for(Taxi k:list)
if(k.dTime <= pTime)
avail.add(k);

if(avail.size()!=0)
at=new Taxi(findLowDist(avail));
}

if(at == null)
throw new NoServiceAvailableException("No taxis available");
else
System.out.println("Taxi can be allocated!");
}

Taxi findLowEarn(List<Taxi> avail)
{
Taxi min=null;
for(Taxi k:avail)
if(k.dTime <= pTime)
min=k;

if(min!=null)
for(Taxi k:avail)
if(k.totEarnings <= min.totEarnings)
min=k;

return min;
}

Taxi findLowDist(List<Taxi> avail)
{
int min =0;
Taxi mt = null;
for(Taxi k:avail)
if(k.dTime<=pTime)
{
min = Math.abs(k.to-fPoint);
mt =k;
}
if(mt !=null)
for(Taxi k:avail)
{
int kmin = Math.abs(k.to-fPoint);
if(kmin <min)
{
min = kmin;
mt =k;
}
}
return mt;
}

void booking()throws Exception
{
at=null;
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.println("Enter Pickup Point:");
fPoint=br.readLine().charAt(0);
if(check(fPoint)==false)
throw new NoServiceAvailableException("Invalid from point");
System.out.println("Enter Drop Point:");
tPoint=br.readLine().charAt(0);
if(check(tPoint)==false)
throw new NoServiceAvailableException("Invalid to point");
System.out.println("Enter Pickup Time:");
pTime=Integer.parseInt(br.readLine());

findTaxi(list);

int dTime = pTime +Math.abs(fPoint-tPoint);
double amount = 100+(((dTime - pTime)*15)-5)*10;
Taxi a=new Taxi(at.taxiNo,bkId++,cId++,fPoint,tPoint,pTime,dTime,amount,(at.totEarnings+amount));

System.out.println("Taxi-"+a.taxiNo+" is alloted");
trips.add(a);

for(Taxi k:list)
if(k.taxiNo == a.taxiNo)
{
list.remove(k);
break;
}

list.add(a);

}

void disp()
{
System.out.println("Taxi No \t Total Earnings \n BookingID \t CustomerID \t From \t To \t PickupTime \t DropTime \t Amount");
for(Taxi k:list)
{
if(k.totEarnings > 0)
System.out.println("Taxi No-"+k.taxiNo+"\t"+"Total Earnings:"+k.totEarnings);
dispAll(k);
System.out.println();
}
}

void dispAll(Taxi t)
{
for(Taxi k:trips)
if(k.taxiNo == t.taxiNo)
System.out.println(k);
}
}

class TaxiAppDriver
{
public static void main(String arg[])throws Exception
{
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.println("Enter required no of taxis:");
int n=Integer.parseInt(br.readLine());
TaxiApp t=new TaxiApp(n);
int ch=0;
do
{
try
{
System.out.println("1 ---> Booking");
System.out.println("2 ---> Display");
System.out.println("3 ---> Exit");
System.out.println("Enter your choice");
ch=Integer.parseInt(br.readLine());
switch(ch)
{
case 1:
t.booking();
break;
case 2:
t.disp();
break;
case 3:
break;
default :
System.out.println("Invalid choice");
}
}catch(Exception ie)
{
System.out.println(ie.getMessage());
}
}while(ch!=3);
}
}













































