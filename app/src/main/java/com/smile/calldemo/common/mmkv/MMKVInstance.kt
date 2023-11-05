package com.smile.calldemo.common.mmkv

/**
 *@Description:
 *
 */
class MMKVInstance {
    companion object {
        val map: HashMap<String, Persistence> = HashMap()
        val mapMulti: HashMap<String, Persistence> = HashMap()

        fun getPersistence(id: String): Persistence {
            return if (map.containsKey(id)) {
                map[id] ?: error("Persistence is null")
            } else {
                val persistence: Persistence = Persistence(MMKVManager.mmkvWithId(id))
                map[id] = persistence
                persistence
            }
        }

        fun getPersistenceMulti(id: String): Persistence {
            return if (mapMulti.containsKey(id)) {
                mapMulti[id] ?: error("Persistence is null")
            } else {
                val persistence: Persistence = Persistence(MMKVManager.mmkvWithID_MULIT(id))
                mapMulti[id] = persistence
                persistence
            }
        }
    }
}