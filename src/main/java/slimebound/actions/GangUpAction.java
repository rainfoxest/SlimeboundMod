package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import slimebound.SlimeboundMod;
import slimebound.orbs.AttackSlime;
import slimebound.orbs.SpawnedSlime;
import slimebound.powers.FirmFortitudePower;
import slimebound.vfx.SlimeDripsEffect;


public class GangUpAction extends AbstractGameAction {
    private AbstractOrb orbType;
    private boolean SelfDamage = true;
    private boolean upgraded = false;
    private int currentAmount;
    private int upgradedamount;
    private int count;
    private boolean firstcast = true;


    public GangUpAction(int count, int damagebuff, boolean firstcast) {

        this.duration = Settings.ACTION_DUR_FAST;

        this.orbType = new AttackSlime();

        this.upgraded = upgraded;
        this.SelfDamage = SelfDamage;
        this.currentAmount = damagebuff;
        SpawnedSlime s = (SpawnedSlime) orbType;
        this.upgradedamount = damagebuff;
        this.count = count;
        this.firstcast = firstcast;

    }


    public void update() {
        int currentHealth = AbstractDungeon.player.currentHealth;

        /*int maxFortitudes = 0;
        if (AbstractDungeon.player.hasPower("FirmFortitudePower")) maxFortitudes = AbstractDungeon.player.getPower("FirmFortitudePower").amount;
        if (AbstractDungeon.player.hasPower("Buffer")) maxFortitudes = maxFortitudes + AbstractDungeon.player.getPower("Buffer").amount;

        int usedFortitudes = 0;*/

        if (SelfDamage) {

            if (currentAmount >= currentHealth && !AbstractDungeon.player.hasPower("Buffer") && !AbstractDungeon.player.hasPower(FirmFortitudePower.POWER_ID)) {
                AbstractDungeon.effectList.add(new SpeechBubble(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, 1.0F, "Need... health...", true));
                this.isDone = true;
                this.count = 0;
                return;
            }
            if (currentAmount > 0) {

                AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, 3, DamageInfo.DamageType.HP_LOSS));

                AbstractDungeon.player.damageFlash = true;
                AbstractDungeon.player.damageFlashFrames = 4;
            }


        }
        //AbstractDungeon.effectsQueue.add(new SlimeDripsEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, 0));
        SlimeboundMod.bumpnextlime = true;


        AbstractDungeon.player.channelOrb(this.orbType);


            AbstractDungeon.actionManager.addToTop(new SlimeBuffUpgraded(this.upgradedamount, SlimeboundMod.mostRecentSlime));

        tickDuration();


        this.isDone = true;
        if (this.count > 0) {
            AbstractDungeon.actionManager.addToBottom(new GangUpAction(this.count - 1,this.upgradedamount,false));
        }

    }
}



