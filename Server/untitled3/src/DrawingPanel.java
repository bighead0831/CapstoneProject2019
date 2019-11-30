import javax.swing.*;
import java.awt.*;

class DrawingPanel extends JPanel
{
    double aain, bbin, ccin, ddin;

    double eein = 0;
    double ffin = 0;
    double ggin = 0;
    double hhin = 0;
    public void setAain(double Aain) { this.aain = Aain;}
    public void setBbin(double Bbin) { this.bbin = Bbin;}
    public void setCcin(double Ccin) { this.ccin = Ccin;}
    public void setDdin(double Ddin) { this.ddin = Ddin;}
    public void setEein(double Eein) { this.eein = Eein;}
    public void setFfin(double Ffin) { this.ffin = Ffin;}
    public void setGgin(double Ggin) { this.ggin = Ggin;}
    public void setHhin(double Hhin) { this.hhin = Hhin;}

    public void paint(Graphics g){
        Reader reader = new Reader();

        /*aain = reader.GetAin();
        bbin = reader.GetBin();
        ccin = reader.GetCin();
        ddin = reader.GetDin();*/

        int aain_height=(int)aain;
        int bbin_height=(int)bbin;
        int ccin_height=(int)ccin;
        int ddin_height=(int)ddin;
        int eein_height=(int)eein;
        int ffin_height=(int)ffin;
        int ggin_height=(int)ggin;
        int hhin_height=(int)hhin;

        g.clearRect(0,0,getWidth(),getHeight());
        g.drawLine(50,350,500,350);
        for(int cnt = 1 ;cnt<11;cnt++)
        {
            g.drawString(cnt *5 +"",25,355-100*cnt);
            g.drawLine(50, 350-100*cnt, 500,350-100*cnt);
        }
        g.drawLine(50,20,50,350);
        g.drawString("Hor_Up",100,370);
        g.drawString("Hor_Down",200,370);
        g.drawString("Ver_Left",300,370);
        g.drawString("Ver_Right",400,370);
        g.setColor(Color.RED);
        if (aain>0)
            g.fillRect(115,350-aain_height*20,10,aain_height*20);
        if(bbin>0)
            g.fillRect(215,350-bbin_height*20,10,bbin_height*20);
        if(ccin>0)
            g.fillRect(315,350-ccin_height*20,10,ccin_height*20);
        if(ddin>0)
            g.fillRect(415,350-ddin_height*20,10,ddin_height*20);
        g.setColor(Color.BLUE);
        if (eein>0)
            g.fillRect(125,350-eein_height*20,10,eein_height*20);
        if(ffin>0)
            g.fillRect(225,350-ffin_height*20,10,ffin_height*20);
        if(ggin>0)
            g.fillRect(325,350-ggin_height*20,10,ggin_height*20);
        if(hhin>0)
            g.fillRect(425,350-hhin_height*20,10,hhin_height*20);
    }
}