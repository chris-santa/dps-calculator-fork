package com.duckblade.osrs.dpscalc.calc.maxhit.magic;

import com.duckblade.osrs.dpscalc.calc.WeaponComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import static com.duckblade.osrs.dpscalc.calc.testutil.SkillsUtil.ofSkill;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PoweredStaffMaxHitComputableTest
{

	@Mock
	private WeaponComputable weaponComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private PoweredStaffMaxHitComputable poweredStaffMaxHitComputable;

	@Test
	void givesMaxHitForTridentOfTheSeas()
	{
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.TRIDENT_OF_THE_SEAS),
			ofItemId(ItemID.TRIDENT_OF_THE_SEAS_E),
			ofItemId(ItemID.TRIDENT_OF_THE_SEAS_FULL)
		);
		when(context.get(ComputeInputs.ATTACKER_SKILLS)).thenReturn(
			ofSkill(Skill.MAGIC, 0),
			ofSkill(Skill.MAGIC, 99),
			ofSkill(Skill.MAGIC, 120)
		);

		assertEquals(1, poweredStaffMaxHitComputable.compute(context));
		assertEquals(28, poweredStaffMaxHitComputable.compute(context));
		assertEquals(35, poweredStaffMaxHitComputable.compute(context));
	}

	@Test
	void givesMaxHitForTridentOfTheSwamp()
	{
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.TRIDENT_OF_THE_SWAMP),
			ofItemId(ItemID.TRIDENT_OF_THE_SWAMP_E)
		);
		when(context.get(ComputeInputs.ATTACKER_SKILLS)).thenReturn(
			ofSkill(Skill.MAGIC, 0),
			ofSkill(Skill.MAGIC, 99)
		);

		assertEquals(4, poweredStaffMaxHitComputable.compute(context));
		assertEquals(31, poweredStaffMaxHitComputable.compute(context));
	}

	@Test
	void givesMaxHitForSangStaff()
	{
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.SANGUINESTI_STAFF),
			ofItemId(ItemID.HOLY_SANGUINESTI_STAFF)
		);
		when(context.get(ComputeInputs.ATTACKER_SKILLS)).thenReturn(
			ofSkill(Skill.MAGIC, 0),
			ofSkill(Skill.MAGIC, 99)
		);

		assertEquals(5, poweredStaffMaxHitComputable.compute(context));
		assertEquals(32, poweredStaffMaxHitComputable.compute(context));
	}

	@Test
	void givesMaxHitForSlayerStaff()
	{
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.SLAYERS_STAFF));
		when(context.get(ComputeInputs.ATTACKER_SKILLS)).thenReturn(
			ofSkill(Skill.MAGIC, 60),
			ofSkill(Skill.MAGIC, 99)
		);

		assertEquals(16, poweredStaffMaxHitComputable.compute(context));
		assertEquals(19, poweredStaffMaxHitComputable.compute(context));
	}

	@Test
	void givesMaxHitForSlayerStaffE()
	{
		when(context.get(weaponComputable)).thenReturn(ofItemId(ItemID.SLAYERS_STAFF_E));
		when(context.get(ComputeInputs.ATTACKER_SKILLS)).thenReturn(
			ofSkill(Skill.MAGIC, 99),
			ofSkill(Skill.MAGIC, 99),
			ofSkill(Skill.MAGIC, 108)
		);
		when(context.get(ComputeInputs.ON_SLAYER_TASK)).thenReturn(true, false, true);

		assertEquals(29, poweredStaffMaxHitComputable.compute(context));
		assertEquals(19, poweredStaffMaxHitComputable.compute(context));
		assertEquals(31, poweredStaffMaxHitComputable.compute(context));
	}

	@Test
	void givesMaxHitForGauntletStaves()
	{
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.CRYSTAL_STAFF_BASIC),
			ofItemId(ItemID.CRYSTAL_STAFF_ATTUNED),
			ofItemId(ItemID.CRYSTAL_STAFF_PERFECTED)
		);

		assertEquals(23, poweredStaffMaxHitComputable.compute(context));
		assertEquals(31, poweredStaffMaxHitComputable.compute(context));
		assertEquals(39, poweredStaffMaxHitComputable.compute(context));
	}

	@Test
	void throwsForUnknownWeapons()
	{
		when(context.get(weaponComputable)).thenReturn(
			ofItemId(ItemID.SMOKE_BATTLESTAFF),
			ofItemId(ItemID.SCYTHE_OF_VITUR)
		);

		assertThrows(IllegalArgumentException.class, () -> poweredStaffMaxHitComputable.compute(context));
	}

}