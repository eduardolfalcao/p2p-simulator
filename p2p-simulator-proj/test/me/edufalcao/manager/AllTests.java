package me.edufalcao.manager;

import me.edufalcao.manager.model.TestPeerRepository;
import me.edufalcao.manager.model.TestRequest;
import me.edufalcao.manager.plugins.accounting.simple.TestAccountingInfo;
import me.edufalcao.manager.plugins.accounting.simple.TestSimpleAccountingPlugin;
import me.edufalcao.manager.plugins.capacitycontroller.fairnessdriven.TestGlobalFairnessDrivenController;
import me.edufalcao.manager.plugins.capacitycontroller.fairnessdriven.TestPairwiseFairnessDrivenController;
import me.edufalcao.manager.plugins.capacitycontroller.fairnessdriven.TestTwoFoldFairnessDrivenController;
import me.edufalcao.manager.plugins.capacitycontroller.satisfactiondriven.TestSatisfactionDrivenCapacityController;
import me.edufalcao.manager.plugins.peerchooser.nonrepeated.TestRandomPeerChooserPlugin;
import me.edufalcao.manager.plugins.scheduler.workloadbased.TestLogReader;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestTimeManager.class,
	TestPeerRepository.class,
	TestRequest.class,
	TestAccountingInfo.class,
	TestSimpleAccountingPlugin.class,
	TestSatisfactionDrivenCapacityController.class,
	TestGlobalFairnessDrivenController.class,
	TestPairwiseFairnessDrivenController.class,
	TestTwoFoldFairnessDrivenController.class,
	TestRandomPeerChooserPlugin.class,
	TestLogReader.class})
public class AllTests {

}
