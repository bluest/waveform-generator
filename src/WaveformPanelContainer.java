import javax.swing.*;
import javax.sound.sampled.AudioInputStream;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Jonathan Simon
 * Date: Mar 20, 2005
 * Time: 5:08:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class WaveformPanelContainer extends JPanel {
    private ArrayList<SingleWaveformPanel> singleChannelWaveformPanels = new ArrayList<SingleWaveformPanel>();
    private AudioInfo audioInfo = null;
    
	public WaveformPanelContainer() {
        setLayout(new GridLayout(0,1));
    }

    public void setAudioToDisplay(AudioInputStream audioInputStream){
        singleChannelWaveformPanels = new ArrayList<SingleWaveformPanel>();
        audioInfo = new AudioInfo(audioInputStream);
        for (int t=0; t<audioInfo.getNumberOfChannels(); t++){
            SingleWaveformPanel waveformPanel
                    = new SingleWaveformPanel(audioInfo, t);
            singleChannelWaveformPanels.add(waveformPanel);
            add(createChannelDisplay(waveformPanel, t));
        }
        
    }

    private JComponent createChannelDisplay(SingleWaveformPanel waveformPanel, int index) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(waveformPanel, BorderLayout.CENTER);

        JLabel label = new JLabel("Channel " + ++index);
        panel.add(label, BorderLayout.NORTH);

        return panel;
    }


}