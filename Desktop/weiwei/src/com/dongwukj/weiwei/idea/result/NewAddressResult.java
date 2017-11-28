package com.dongwukj.weiwei.idea.result;

import java.util.ArrayList;

public class NewAddressResult extends BaseResult {
	private ArrayList<NewAddressEntity> regions;

	
	public ArrayList<NewAddressEntity> getRegions() {
		return regions;
	}


	public void setRegions(ArrayList<NewAddressEntity> regions) {
		this.regions = regions;
	}

	
	@Override
	public String toString() {
		return "NewAddressResult [regions=" + regions + "]";
	}


	public static class NewAddressEntity {
		private String displayOrder;
		private String layer;
		private String name;
		private int parentId;
		private int regionId;
		private String shortSpell;
		private String spell;

		public String getDisplayOrder() {
			return displayOrder;
		}

		public void setDisplayOrder(String displayOrder) {
			this.displayOrder = displayOrder;
		}

		public String getLayer() {
			return layer;
		}

		public void setLayer(String layer) {
			this.layer = layer;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getParentId() {
			return parentId;
		}

		public void setParentId(int parentId) {
			this.parentId = parentId;
		}

		public int getRegionId() {
			return regionId;
		}

		public void setRegionId(int regionId) {
			this.regionId = regionId;
		}

		public String getShortSpell() {
			return shortSpell;
		}

		public void setShortSpell(String shortSpell) {
			this.shortSpell = shortSpell;
		}

		public String getSpell() {
			return spell;
		}

		public void setSpell(String spell) {
			this.spell = spell;
		}

		@Override
		public String toString() {
			return "NewAddressEntity [displayOrder=" + displayOrder
					+ ", layer=" + layer + ", name=" + name + ", parentId="
					+ parentId + ", regionId=" + regionId + ", shortSpell="
					+ shortSpell + ", spell=" + spell + "]";
		}

	}
}
