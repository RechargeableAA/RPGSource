package com.rech.rpg.item;

import com.rech.rpg.entity.Entity;
import com.rech.rpg.item.Item;
import com.rech.rpg.item.Weapon;

public class ItemPhrase extends Item{
			
		String actionPhrase;
		String contactPhrase;
		
		//TODO finisher phrases?

		protected ItemPhrase(Item item) {

			this.actionPhrase = null; //TODO
			this.contactPhrase = null; //TODO
		}
		
		/**
		 * Verbiage used when using the item. 
		 * @param actionPhrase - Describes the action in present tense
		 * @param contactPhrase - Describes the action in past tense after the action was done.
		 * EXAMPLE - You SWING your sword and SLICE the enemy. 
		 * You LUNGE your dagger and STAB the enemy.
		 * 
		 * @implNote create new phrases with implemented spacing in your strings.
		 */
		private void getWeaponPhrase(Entity entity) {
			//action
			if (entity.getEquipped().type.equals(Weapon.Type.unarmed) 	 || 
				entity.getEquipped().type.equals(Weapon.Type.shortSword) ||
				entity.getEquipped().type.equals(Weapon.Type.scimitar) 	 ||
				entity.getEquipped().type.equals(Weapon.Type.mace) 		 ||
				entity.getEquipped().type.equals(Weapon.Type.longSword)  ||
				entity.getEquipped().type.equals(Weapon.Type.katana) 	 ||
				entity.getEquipped().type.equals(Weapon.Type.hatchet) 	 ||
				entity.getEquipped().type.equals(Weapon.Type.greatHammer)||
				entity.getEquipped().type.equals(Weapon.Type.greatSword)){
				actionPhrase = " swing ";
			}else if (entity.getEquipped().type.equals(Weapon.Type.dagger) ||
					  entity.getEquipped().type.equals(Weapon.Type.rapier)) {
				actionPhrase = " lunge ";
			}
			
			//contact
			if (entity.getEquipped().type.equals(Weapon.Type.unarmed)) {
				contactPhrase = " punch ";
			}else if (entity.getEquipped().type.equals(Weapon.Type.dagger) || (entity.getEquipped().type.equals(Weapon.Type.rapier))) {
				contactPhrase = " stab into ";
			}else if (entity.getEquipped().type.equals(Weapon.Type.mace)){
				contactPhrase = " bash ";
			}else if (entity.getEquipped().type.equals(Weapon.Type.greatHammer)) {
				contactPhrase = " crush ";
			}//TODO add the other weapon phrases
		}
	}

