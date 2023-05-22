package userinterface.graphical;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import game.location.GameStart;

/**
 * A class that defines the first setup form to be displayed at the start of the
 * game. Consists of inputs for setting game preferences, including the team
 * name, game seed, season length and difficulty.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SetupForm1 extends GUILocation {
	/**
	 * Constructor for the SetupForm1 component. Provides the user with a graphical
	 * interface to choose game preferences before the game begins.
	 * 
	 * @param gameLocation   The GUI location's corresponding game location class.
	 * @param guiEnvironment The GUI environment to which the GUI location belongs.
	 */
	public SetupForm1(GameStart gameLocation, GUIEnvironment guiEnvironment, Runnable onSubmit) {
		super(guiEnvironment);

		setBounds(6, 64, 788, 504);
		setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("58px"), ColumnSpec.decode("140px"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("146px"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("114px"), },
				new RowSpec[] { FormSpecs.LINE_GAP_ROWSPEC, RowSpec.decode("21px"), FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel teamNameLabel = new JLabel("Team Name");
		teamNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(teamNameLabel, "2, 2, right, center");

		JTextField teamNameTextField;
		teamNameTextField = new JTextField();
		teamNameLabel.setLabelFor(teamNameTextField);
		add(teamNameTextField, "4, 2, left, top");
		teamNameTextField.setColumns(10);

		JLabel seedLabel = new JLabel("Seed");
		add(seedLabel, "2, 4, right, default");

		JSpinner seedSpinner;
		seedSpinner = new JSpinner();
		seedSpinner.setPreferredSize(new Dimension(114, 22));
		seedSpinner.setMinimumSize(new Dimension(100, 22));
		seedLabel.setLabelFor(seedSpinner);
		add(seedSpinner, "4, 4, left, top");

		JLabel difficultyLabel = new JLabel("Difficulty");
		add(difficultyLabel, "2, 6, right, default");

		JSlider difficultySlider = new JSlider();
		difficultyLabel.setLabelFor(difficultySlider);
		difficultySlider.setPaintTicks(true);
		difficultySlider.setSnapToTicks(true);
		difficultySlider.setMinorTickSpacing(1);
		difficultySlider.setMajorTickSpacing(1);
		difficultySlider.setMinimum(1);
		difficultySlider.setMaximum(3);
		add(difficultySlider, "4, 6");

		JLabel weeksLabel = new JLabel("Season Length (weeks)");
		add(weeksLabel, "2, 8, right, default");

		JSlider weeksSlider = new JSlider();
		weeksLabel.setLabelFor(weeksSlider);
		weeksSlider.setPaintTicks(true);
		weeksSlider.setSnapToTicks(true);
		weeksSlider.setMinorTickSpacing(1);
		weeksSlider.setMajorTickSpacing(5);
		weeksSlider.setPaintLabels(true);
		weeksSlider.setMinimum(5);
		weeksSlider.setMaximum(15);
		add(weeksSlider, "4, 8");

		JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String teamName = teamNameTextField.getText();
				// Make sure team name isn't empty.
				if (teamName.length() < 3 || 15 < teamName.length()) {
					guiEnvironment.displayPopup("Please enter a team name between 3 and 15 characters long.");
					return;
				}

				// Make sure the seed is a number.
				try {
					seedSpinner.commitEdit();
				} catch (java.text.ParseException exception) {
					guiEnvironment.displayPopup("Please enter a number for the seed.");
					return;
				}
				Integer seed = (Integer) seedSpinner.getValue();

				int difficulty = difficultySlider.getValue();
				int seasonLength = weeksSlider.getValue();

				// Pass the input values to the game location.
				gameLocation.setTeamName(teamName);
				gameLocation.setSeed(seed);
				gameLocation.setDifficulty(difficulty);
				gameLocation.setSeasonLength(seasonLength);

				onSubmit.run();
			}
		});
		add(submitBtn, "4, 12");
	}
}
