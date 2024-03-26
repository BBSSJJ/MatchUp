# Riot Developer Portal

## LEAGUE OF LEGENDS

### Obtaining PUUID and summonerID from Riot ID

#### Get accounts by riot id

obtain the PUUID associated with a given account by Riot ID (gameName + tagLine)

```py
@lol.get("/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}")

    # RESPONSE BODY
    return {
        "puuid": "encryptedPUUID",
        "gameName": "티모석",
        "tagLine": "KR1"
    }
```

#### Get a summoner by PUUID

retrieve summoner data by PUUID, including summonerID

```py
@lol.get("/lol/summoner/v4/summoners/by-puuid/{encryptedPUUID}")

    # RESPONSE BODY
    return {
        "id": "encryptedSummonerID",
        "accountId": "encryptedAccountID",
        "puuid": "encryptedPUUID",
        "name": "티모석",
        "profileIconId": 5182,
        "revisionDate": 1708442987000,
        "summonerLevel": 194
    }
```

### Obtaining Riot ID from PUUID

#### Get account by puuid

fetch account information (gameName + tagLine) by PUUID

```py
@lol.get("/lol/summoner/v4/summoners/by-puuid/{encryptedPUUID}")

    # RESPONSE BODY
    return {
        "puuid": "encryptedPUUID",
        "gameName": "티모석",
        "tagLine": "KR1"
    }
```

### Obtaining Riot ID from summonerID

retrieve summoner data by summonerID, which can be used to obtain the corresponding PUUID

```py
@lol.get("/lol/summoner/v4/summoners/by-puuid/{encryptedPUUID}")

    # RESPONSE BODY
    return {
        "id": "encryptedSummonerID",
        "accountId": "Zb41Y5vce7RzDnpOk5U4A06c-5UvhNsDzJm08ROcKDTf3tM",
        "puuid": "encryptedPUUID",
        "name": "티모석",
        "profileIconId": 5182,
        "revisionDate": 1708442987000,
        "summonerLevel": 194
    }
```

### Data Dragon

download a compressed tarball (.tgz) for each patch that contains all assets for that patch

[Latest Version](https://ddragon.leagueoflegends.com/cdn/dragontail-14.4.1.tgz)

### Versions

find all valid Data Dragon versions in the versions file

```py
@ddragon.get("https://ddragon.leagueoflegends.com/api/versions.json")
    
    # RESPONSE BODY
    return {
        [
            "14.4.1",
            "lolpatch_3.7",
        ]
    }
```

### Regions

find the version each region is using in the realms files

```py
@ddragon.get("https://ddragon.leagueoflegends.com/realms/kr.json")
    
    # RESPONSE BODY
    return {
        "n": {
            "item": "14.4.1",
            "rune": "7.23.1",
            "mastery": "7.23.1",
            "summoner": "14.4.1",
            "champion": "14.4.1",
            "profileicon": "14.4.1",
            "map": "14.4.1",
            "language": "14.4.1",
            "sticker": "14.4.1"
        },
        "v": "14.4.1",
        "l": "en_US",
        "cdn": "https://ddragon.leagueoflegends.com/cdn",
        "dd": "14.4.1",
        "lg": "14.4.1",
        "css": "14.4.1",
        "profileiconmax": 28,
        "store": null
    }
```

### Languages

localized versions of each of the data files in languages supported by the client

```py
@ddragon.get("https://ddragon.leagueoflegends.com/cdn/languages.json")
    
    # RESPONSE BODY
    return {
        [
            "en_US",
            "cs_CZ",
            "de_DE",
            "el_GR",
            "en_AU",
            "en_GB",
            "en_PH",
            "en_SG",
            "es_AR",
            "es_ES",
            "es_MX",
            "fr_FR",
            "hu_HU",
            "it_IT",
            "ja_JP",
            "ko_KR",
            "pl_PL",
            "pt_BR",
            "ro_RO",
            "ru_RU",
            "th_TH",
            "tr_TR",
            "vi_VN",
            "zh_CN",
            "zh_MY",
            "zh_TW"
        ]
    }
```

### Champions

a list of champions with a brief summary

```py
@ddragon.get("https://ddragon.leagueoflegends.com/cdn/14.4.1/data/en_US/champion.json")
    
    # RESPONSE BODY
    return {
        "type": "champion",
        "format": "standAloneComplex",
        "version": "14.4.1",
        "data": {
            "Aatrox": {
                "version": "14.4.1",
                "id": "Aatrox",
                "key": "266",
                "name": "아트록스",
                "title": "다르킨의 검",
                "blurb": "한때는 공허에 맞서 싸웠던 슈리마의 명예로운 수호자 아트록스와 그의 종족은 결국 공허보다 위험한 존재가 되어 룬테라의 존속을 위협했지만, 교활한 필멸자의 마법에 속아넘어가 패배하게 되었다. 수백 년에 걸친 봉인 끝에, 아트록스는 자신의 정기가 깃든 마법 무기를 휘두르는 어리석은 자들을 타락시키고 육신을 바꾸는 것으로 다시 한번 자유의 길을 찾아내었다. 이제 이전의 잔혹한 모습을 닮은 육체를 차지한 아트록스는 세상의 종말과 오랫동안 기다려온 복수를...",
                "info": {
                    "attack": 8,
                    "defense": 4,
                    "magic": 3,
                    "difficulty": 4
                },
                "image": {
                    "full": "Aatrox.png",
                    "sprite": "champion0.png",
                    "group": "champion",
                    "x": 0,
                    "y": 0,
                    "w": 48,
                    "h": 48
                },
                "tags": [
                    "Fighter",
                    "Tank"
                ],
                "partype": "피의 샘",
                "stats": {
                    "hp": 650,
                    "hpperlevel": 114,
                    "mp": 0,
                    "mpperlevel": 0,
                    "movespeed": 345,
                    "armor": 38,
                    "armorperlevel": 4.45,
                    "spellblock": 32,
                    "spellblockperlevel": 2.05,
                    "attackrange": 175,
                    "hpregen": 3,
                    "hpregenperlevel": 1,
                    "mpregen": 0,
                    "mpregenperlevel": 0,
                    "crit": 0,
                    "critperlevel": 0,
                    "attackdamage": 60,
                    "attackdamageperlevel": 5,
                    "attackspeedperlevel": 2.5,
                    "attackspeed": 0.651
                }
            },
        }
    }
```

additional data for each champion

```py
@ddragon.get("https://ddragon.leagueoflegends.com/cdn/14.4.1/data/ko_KR/champion/Aatrox.json")
    
    # RESPONSE BODY
    return {
        "type": "champion",
        "format": "standAloneComplex",
        "version": "14.4.1",
        "data": {
            "Aatrox": {
                "id": "Aatrox",
                "key": "266",
                "name": "아트록스",
                "title": "다르킨의 검",
                "image": {
                    "full": "Aatrox.png",
                    "sprite": "champion0.png",
                    "group": "champion",
                    "x": 0,
                    "y": 0,
                    "w": 48,
                    "h": 48
                },
                "skins": [
                    {
                        "id": "266000",
                        "num": 0,
                        "name": "default",
                        "chromas": false
                    },
                    {
                        "id": "266001",
                        "num": 1,
                        "name": "정의의 아트록스",
                        "chromas": false
                    },
                    {
                        "id": "266002",
                        "num": 2,
                        "name": "메카 아트록스",
                        "chromas": true
                    },
                    {
                        "id": "266003",
                        "num": 3,
                        "name": "바다 사냥꾼 아트록스",
                        "chromas": false
                    },
                    {
                        "id": "266007",
                        "num": 7,
                        "name": "핏빛달 아트록스",
                        "chromas": false
                    },
                    {
                        "id": "266008",
                        "num": 8,
                        "name": "프레스티지 핏빛달 아트록스",
                        "chromas": false
                    },
                    {
                        "id": "266009",
                        "num": 9,
                        "name": "승리의 아트록스",
                        "chromas": true
                    },
                    {
                        "id": "266011",
                        "num": 11,
                        "name": "오디세이 아트록스",
                        "chromas": true
                    },
                    {
                        "id": "266020",
                        "num": 20,
                        "name": "프레스티지 핏빛달 아트록스 (2022)",
                        "chromas": false
                    },
                    {
                        "id": "266021",
                        "num": 21,
                        "name": "달을 삼킨 아트록스",
                        "chromas": true
                    },
                    {
                        "id": "266030",
                        "num": 30,
                        "name": "DRX 아트록스",
                        "chromas": true
                    },
                    {
                        "id": "266031",
                        "num": 31,
                        "name": "프레스티지 DRX 아트록스",
                        "chromas": false
                    }
                ],
                "lore": "한때는 공허에 맞서 싸웠던 슈리마의 명예로운 수호자 아트록스와 그의 종족은 결국 공허보다 위험한 존재가 되어 룬테라의 존속을 위협했지만, 교활한 필멸자의 마법에 속아넘어가 패배하게 되었다. 수백 년에 걸친 봉인 끝에, 아트록스는 자신의 정기가 깃든 마법 무기를 휘두르는 어리석은 자들을 타락시키고 육신을 바꾸는 것으로 다시 한번 자유의 길을 찾아내었다. 이제 이전의 잔혹한 모습을 닮은 육체를 차지한 아트록스는 세상의 종말과 오랫동안 기다려온 복수를 열망한다.",
                "blurb": "한때는 공허에 맞서 싸웠던 슈리마의 명예로운 수호자 아트록스와 그의 종족은 결국 공허보다 위험한 존재가 되어 룬테라의 존속을 위협했지만, 교활한 필멸자의 마법에 속아넘어가 패배하게 되었다. 수백 년에 걸친 봉인 끝에, 아트록스는 자신의 정기가 깃든 마법 무기를 휘두르는 어리석은 자들을 타락시키고 육신을 바꾸는 것으로 다시 한번 자유의 길을 찾아내었다. 이제 이전의 잔혹한 모습을 닮은 육체를 차지한 아트록스는 세상의 종말과 오랫동안 기다려온 복수를...",
                "allytips": [
                    "공격 성공률을 높이려면 다르킨의 검을 사용하는 동안 파멸의 돌진을 사용하세요.",
                    "지옥사슬 같은 군중 제어 스킬이나 아군의 이동 불가 효과를 통해 다르킨의 검을 쉽게 적중시킬 수 있습니다.",
                    "전투를 시작할 준비가 되면 세계의 종결자를 사용하세요."
                ],
                "enemytips": [
                    "아트록스의 공격은 미리 표시되므로 공격이 예상되는 지역에서 벗어나세요.",
                    "아트록스를 향해 이동하거나 양옆으로 이동하면 지옥사슬에서 더 쉽게 벗어날 수 있습니다.",
                    "아트록스가 궁극기를 사용하면 부활하지 못하도록 거리를 유지하세요."
                ],
                "tags": [
                    "Fighter",
                    "Tank"
                ],
                "partype": "피의 샘",
                "info": {
                    "attack": 8,
                    "defense": 4,
                    "magic": 3,
                    "difficulty": 4
                },
                "stats": {
                    "hp": 650,
                    "hpperlevel": 114,
                    "mp": 0,
                    "mpperlevel": 0,
                    "movespeed": 345,
                    "armor": 38,
                    "armorperlevel": 4.45,
                    "spellblock": 32,
                    "spellblockperlevel": 2.05,
                    "attackrange": 175,
                    "hpregen": 3,
                    "hpregenperlevel": 1,
                    "mpregen": 0,
                    "mpregenperlevel": 0,
                    "crit": 0,
                    "critperlevel": 0,
                    "attackdamage": 60,
                    "attackdamageperlevel": 5,
                    "attackspeedperlevel": 2.5,
                    "attackspeed": 0.651
                },
                "spells": [
                    {
                        "id": "AatroxQ",
                        "name": "다르킨의 검",
                        "description": "아트록스가 대검을 내리쳐 물리 피해를 줍니다. 세 번까지 휘두를 수 있으며 각 공격은 피해 범위가 다릅니다.",
                        "tooltip": "아트록스가 대검을 내리쳐 <physicalDamage>{{ qdamage }}의 물리 피해</physicalDamage>를 입힙니다. 끝에 적중한 적을 잠깐 <status>공중으로 띄워 올리고</status> <physicalDamage>{{ qedgedamage }}</physicalDamage>의 피해를 입힙니다. 이 스킬은 두 번 <recast>재사용</recast>할 수 있으며 다시 사용할 때마다 범위 모양이 변하고 이전보다 25% 더 많은 피해를 입힙니다.{{ spellmodifierdescriptionappend }}",
                        "leveltip": {
                            "label": [
                                "재사용 대기시간",
                                "피해량",
                                "총 공격력 %"
                            ],
                            "effect": [
                                "{{ cooldown }} -> {{ cooldownNL }}",
                                "{{ qbasedamage }} -> {{ qbasedamageNL }}",
                                "{{ qtotaladratio*100.000000 }}% -> {{ qtotaladrationl*100.000000 }}%"
                            ]
                        },
                        "maxrank": 5,
                        "cooldown": [
                            14,
                            12,
                            10,
                            8,
                            6
                        ],
                        "cooldownBurn": "14/12/10/8/6",
                        "cost": [
                            0,
                            0,
                            0,
                            0,
                            0
                        ],
                        "costBurn": "0",
                        "datavalues": {},
                        "effect": [
                            null,
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ]
                        ],
                        "effectBurn": [
                            null,
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0"
                        ],
                        "vars": [],
                        "costType": "소모값 없음",
                        "maxammo": "-1",
                        "range": [
                            25000,
                            25000,
                            25000,
                            25000,
                            25000
                        ],
                        "rangeBurn": "25000",
                        "image": {
                            "full": "AatroxQ.png",
                            "sprite": "spell0.png",
                            "group": "spell",
                            "x": 384,
                            "y": 48,
                            "w": 48,
                            "h": 48
                        },
                        "resource": "소모값 없음"
                    },
                    {
                        "id": "AatroxW",
                        "name": "지옥사슬",
                        "description": "아트록스가 지면을 내리쳐 처음 맞힌 적에게 피해를 줍니다. 대상이 챔피언 또는 대형 몬스터인 경우 일정 시간 안에 해당 지역을 벗어나지 않으면 중앙으로 끌려가 다시 피해를 받습니다.",
                        "tooltip": "아트록스가 사슬을 발사하여 처음 적중한 적을 {{ wslowduration }}초 동안 {{ wslowpercentage*-100 }}%만큼 <status>둔화</status>시키고 <magicDamage>{{ wdamage }}의 마법 피해</magicDamage>를 입힙니다. 챔피언과 대형 정글 몬스터는 {{ wslowduration }}초 안에 해당 지역을 벗어나지 않으면 중심으로 <status>끌려가</status> 다시 같은 양의 피해를 입습니다.{{ spellmodifierdescriptionappend }}",
                        "leveltip": {
                            "label": [
                                "재사용 대기시간",
                                "피해량",
                                "둔화"
                            ],
                            "effect": [
                                "{{ cooldown }} -> {{ cooldownNL }}",
                                "{{ wbasedamage }} -> {{ wbasedamageNL }}",
                                "{{ wslowpercentage*-100.000000 }}% -> {{ wslowpercentagenl*-100.000000 }}%"
                            ]
                        },
                        "maxrank": 5,
                        "cooldown": [
                            20,
                            18,
                            16,
                            14,
                            12
                        ],
                        "cooldownBurn": "20/18/16/14/12",
                        "cost": [
                            0,
                            0,
                            0,
                            0,
                            0
                        ],
                        "costBurn": "0",
                        "datavalues": {},
                        "effect": [
                            null,
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ]
                        ],
                        "effectBurn": [
                            null,
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0"
                        ],
                        "vars": [],
                        "costType": "소모값 없음",
                        "maxammo": "-1",
                        "range": [
                            825,
                            825,
                            825,
                            825,
                            825
                        ],
                        "rangeBurn": "825",
                        "image": {
                            "full": "AatroxW.png",
                            "sprite": "spell0.png",
                            "group": "spell",
                            "x": 432,
                            "y": 48,
                            "w": 48,
                            "h": 48
                        },
                        "resource": "소모값 없음"
                    },
                    {
                        "id": "AatroxE",
                        "name": "파멸의 돌진",
                        "description": "기본 지속 효과로 아트록스가 적 챔피언에게 피해를 입히면 체력을 회복합니다. 사용 시, 아트록스가 지정한 방향으로 돌진합니다.",
                        "tooltip": "<spellPassive>기본 지속 효과:</spellPassive> 아트록스가 챔피언에게 가한 피해의 <lifeSteal>{{ espellvamp }}%</lifeSteal>만큼 체력을 회복합니다. <keywordMajor>세계의 종결자</keywordMajor> 상태일 때는 효과가 <lifeSteal>{{ espellvampempowered }}%</lifeSteal>로 증가합니다.<br /><br /><spellActive>사용 시:</spellActive> 아트록스가 돌진합니다. 이 스킬은 다른 스킬이 진행되는 동안 사용할 수 있습니다.{{ spellmodifierdescriptionappend }}",
                        "leveltip": {
                            "label": [
                                "재사용 대기시간",
                                "회복량 %",
                                "세계의 종결자 중 회복량 %"
                            ],
                            "effect": [
                                "{{ cooldown }} -> {{ cooldownNL }}",
                                "{{ espellvamp }}% -> {{ espellvampNL }}%",
                                "{{ espellvampempowered }}% -> {{ espellvampempoweredNL }}%"
                            ]
                        },
                        "maxrank": 5,
                        "cooldown": [
                            9,
                            8,
                            7,
                            6,
                            5
                        ],
                        "cooldownBurn": "9/8/7/6/5",
                        "cost": [
                            0,
                            0,
                            0,
                            0,
                            0
                        ],
                        "costBurn": "0",
                        "datavalues": {},
                        "effect": [
                            null,
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0,
                                0,
                                0
                            ]
                        ],
                        "effectBurn": [
                            null,
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0"
                        ],
                        "vars": [],
                        "costType": "소모값 없음",
                        "maxammo": "1",
                        "range": [
                            25000,
                            25000,
                            25000,
                            25000,
                            25000
                        ],
                        "rangeBurn": "25000",
                        "image": {
                            "full": "AatroxE.png",
                            "sprite": "spell0.png",
                            "group": "spell",
                            "x": 0,
                            "y": 96,
                            "w": 48,
                            "h": 48
                        },
                        "resource": "소모값 없음"
                    },
                    {
                        "id": "AatroxR",
                        "name": "세계의 종결자",
                        "description": "아트록스가 악마의 힘을 해방하여 근처 적 미니언에게 공포를 주고 자신의 공격력과 체력 회복량, 이동 속도가 증가합니다. 아트록스가 챔피언 처치에 관여하면 이 효과의 지속시간이 연장됩니다.",
                        "tooltip": "아트록스가 진정한 악마의 모습을 드러내 근처 미니언이 {{ rminionfearduration }}초 동안 <status>공포</status>에 떨게 하고 <speed>이동 속도가 {{ rmovementspeedbonus*100 }}%</speed> 증가했다가 {{ rduration }}초에 걸쳐 원래대로 돌아옵니다. 지속시간 동안 <scaleAD>공격력이 {{ rtotaladamp*100 }}%</scaleAD>, <healing>자신에 대한 체력 회복 효과가 {{ rhealingamp*100 }}%</healing> 증가합니다.<br /><br />챔피언 처치 관여 시 이 효과의 지속시간이 {{ rextension }}초 늘어나고 <speed>이동 속도</speed> 효과가 초기화됩니다.{{ spellmodifierdescriptionappend }}",
                        "leveltip": {
                            "label": [
                                "총 공격력 증가",
                                "회복량 증가",
                                "이동 속도",
                                "재사용 대기시간"
                            ],
                            "effect": [
                                "{{ rtotaladamp*100.000000 }}% -> {{ rtotaladampnl*100.000000 }}%",
                                "{{ rhealingamp*100.000000 }}% -> {{ rhealingampnl*100.000000 }}%",
                                "{{ rmovementspeedbonus*100.000000 }}% -> {{ rmovementspeedbonusnl*100.000000 }}%",
                                "{{ cooldown }} -> {{ cooldownNL }}"
                            ]
                        },
                        "maxrank": 3,
                        "cooldown": [
                            120,
                            100,
                            80
                        ],
                        "cooldownBurn": "120/100/80",
                        "cost": [
                            0,
                            0,
                            0
                        ],
                        "costBurn": "0",
                        "datavalues": {},
                        "effect": [
                            null,
                            [
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0
                            ],
                            [
                                0,
                                0,
                                0
                            ]
                        ],
                        "effectBurn": [
                            null,
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0",
                            "0"
                        ],
                        "vars": [],
                        "costType": "소모값 없음",
                        "maxammo": "-1",
                        "range": [
                            25000,
                            25000,
                            25000
                        ],
                        "rangeBurn": "25000",
                        "image": {
                            "full": "AatroxR.png",
                            "sprite": "spell0.png",
                            "group": "spell",
                            "x": 48,
                            "y": 96,
                            "w": 48,
                            "h": 48
                        },
                        "resource": "소모값 없음"
                    }
                ],
                "passive": {
                    "name": "사신 태세",
                    "description": "주기적으로 아트록스의 기본 공격이 대상 최대 체력에 비례하여 추가 <physicalDamage>물리 피해</physicalDamage>를 입히고 자신의 체력을 회복합니다. ",
                    "image": {
                        "full": "Aatrox_Passive.png",
                        "sprite": "passive0.png",
                        "group": "passive",
                        "x": 0,
                        "y": 0,
                        "w": 48,
                        "h": 48
                    }
                },
                "recommended": []
            }
        }
    }
```

#### Champion Splash Assets

find the skin number for each skin in the file for each individual champion in Data Dragon

```py
@ddragon.get("https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Aatrox_0.jpg")
    
    # RESPONSE BODY
    return Image
```

#### Champion Loading Screen Assets

```py
@ddragon.get("https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Aatrox_0.jpg")
    
    # RESPONSE BODY
    return Image
```

#### Champion Square Assets

```py
@ddragon.get("https://ddragon.leagueoflegends.com/cdn/14.4.1/img/champion/Aatrox.png")
    
    # RESPONSE BODY
    return Image
```

#### Champion Passive Assets

```py
@ddragon.get("https://ddragon.leagueoflegends.com/cdn/14.4.1/img/passive/Anivia_P.png")
    
    # RESPONSE BODY
    return Image
```

#### Champion Ability Assets

```py
@ddragon.get("https://ddragon.leagueoflegends.com/cdn/14.4.1/img/spell/FlashFrost.png")
    
    # RESPONSE BODY
    return Image
```

### Items

detail for every item in the game

```py
@ddragon.get("https://ddragon.leagueoflegends.com/cdn/14.4.1/data/en_US/item.json")
    
    # RESPONSE BODY
    return {}
```

#### Item Assets

```py
@ddragon.get("https://ddragon.leagueoflegends.com/cdn/14.4.1/img/item/1001.png")
    
    # RESPONSE BODY
    return Image
```

### Other

- Summoer Spells
  - Json
  - Image
- Profile Icons
  - Json
  - Image
- Minimaps Image
  - Image
- Sprites
  - Image
- Scoreboard Icons
  - Champion Image
  - Item Image
  - minion Image
  - Score Image
  - Spells Image

### ACCOUNT-V1

Get account by puuid

```py
@lol.get("/riot/account/v1/accounts/by-puuid/{puuid}")

    # RESPONSE BODY
    return {
        "puuid": "encryptedPUUID",
        "gameName": "티모석",
        "tagLine": "KR1"
    }
```

Get active shard for a player

```py
@lol.get("/riot/account/v1/active-shards/by-game/{game}/by-puuid/{puuid}")

    # RESPONSE BODY
    return {
        "puuid": "encryptedPUUID",
        "gameName": "티모석",
        "tagLine": "KR1"
    }
```

### CHAMPION-MASTERY-V4

Get all champion mastery entries sorted by number of champion points descending

```py
@lol.get("/lol/champion-mastery/v4/champion-masteries/by-puuid/{encryptedPUUID}")

    # RESPONSE BODY
    return [
        {
            "puuid": "encryptedPUUID",
            "championId": 17,
            "championLevel": 5,
            "championPoints": 86926,
            "lastPlayTime": 1703065605000,
            "championPointsSinceLastLevel": 65326,
            "championPointsUntilNextLevel": 0,
            "chestGranted": false,
            "tokensEarned": 2,
            "summonerId": "encryptedSummonerID"
        },
    ]
```

Get a champion mastery by puuid and champion ID

```py
@lol.get("/lol/champion-mastery/v4/champion-masteries/by-puuid/{encryptedPUUID}/by-champion/{championId}")

    # RESPONSE BODY
    return {
        "puuid": "encryptedPUUID",
        "championId": 17,
        "championLevel": 5,
        "championPoints": 86926,
        "lastPlayTime": 1703065605000,
        "championPointsSinceLastLevel": 65326,
        "championPointsUntilNextLevel": 0,
        "chestGranted": false,
        "tokensEarned": 2,
        "summonerId": "encryptedSummonerID"
    }
```

Get specified number of top champion mastery entries sorted by number of champion points descending

```py
@lol.get("/lol/champion-mastery/v4/champion-masteries/by-puuid/{encryptedPUUID}/top")
async def top_champion_mastery_entries(count: int = 3):

    # RESPONSE BODY
    return [
        {
            "puuid": "encryptedPUUID",
            "championId": 17,
            "championLevel": 5,
            "championPoints": 86926,
            "lastPlayTime": 1703065605000,
            "championPointsSinceLastLevel": 65326,
            "championPointsUntilNextLevel": 0,
            "chestGranted": false,
            "tokensEarned": 2,
            "summonerId": "encryptedSummonerID"
        },
        {
            "puuid": "encryptedPUUID",
            "championId": 43,
            "championLevel": 5,
            "championPoints": 61374,
            "lastPlayTime": 1702890781000,
            "championPointsSinceLastLevel": 39774,
            "championPointsUntilNextLevel": 0,
            "chestGranted": false,
            "tokensEarned": 2,
            "summonerId": "encryptedSummonerID"
        },
        {
            "puuid": "encryptedPUUID",
            "championId": 12,
            "championLevel": 5,
            "championPoints": 59493,
            "lastPlayTime": 1696091443000,
            "championPointsSinceLastLevel": 37893,
            "championPointsUntilNextLevel": 0,
            "chestGranted": false,
            "tokensEarned": 2,
            "summonerId": "encryptedSummonerID"
        }
    ]
```

Get a player's total champion mastery score, which is the sum of individual champion mastery levels

```py
@lol.get("/lol/champion-mastery/v4/scores/by-puuid/{encryptedPUUID}")

    # RESPONSE BODY
    return 333
```

### CHAMPION-V3

Returns champion rotations, including free-to-play and low-level free-to-play rotations

```py
@lol.get("/lol/platform/v3/champion-rotations")

    # RESPONSE BODY
    return {
        "freeChampionIds": [
            17,
            18,
            25,
            42,
            43,
            45,
            56,
            57,
            62,
            64,
            68,
            75,
            79,
            84,
            101,
            110,
            117,
            119,
            142,
            145,
            268,
            350,
            432,
            516,
            517,
            887
        ],
        "freeChampionIdsForNewPlayers": [
            222,
            254,
            33,
            82,
            131,
            350,
            54,
            17,
            18,
            37,
            51,
            145,
            134,
            89,
            875,
            80,
            115,
            91,
            113,
            112
        ],
        "maxNewPlayerLevel": 10
    }
```

### LEAGUE-EXP-V4

Get all the league entries

```py
@lol.get("/lol/league-exp/v4/entries/{queue}/{tier}/{division}")
async def all_league_entries(page: int = 1):

    # RESPONSE BODY
    return [
        {
            "leagueId": "encryptedLeagueID",
            "queueType": "RANKED_FLEX_SR",
            "tier": "PLATINUM",
            "rank": "IV",
            "summonerId": "encryptedSummonerID",
            "summonerName": "티모석",
            "leaguePoints": 4,
            "wins": 5,
            "losses": 3,
            "veteran": false,
            "inactive": false,
            "freshBlood": false,
            "hotStreak": false
        },
    ]
```

### LEAGUE-V4

Get the challenger league for given queue

```py
@lol.get("/lol/league/v4/challengerleagues/by-queue/{queue}")

    # RESPONSE BODY
    return {
        "tier": "CHALLENGER",
        "leagueId": "encryptedLeagueID",
        "queue": "RANKED_SOLO_5x5",
        "name": "Sona's Sentinels",
        "entries": [
            {
                "summonerId": "encryptedSummonerID",
                "summonerName": "Peyz",
                "leaguePoints": 1576,
                "rank": "I",
                "wins": 175,
                "losses": 112,
                "veteran": true,
                "inactive": false,
                "freshBlood": false,
                "hotStreak": true
            },
        ]
    }
```

Get league entries in all queues for a given summoner ID

```py
@lol.get("/lol/league/v4/challengerleagues/by-queue/{queue}")

    # RESPONSE BODY
    return [
        {
            "leagueId": "encryptedLeagueID",
            "queueType": "RANKED_FLEX_SR",
            "tier": "PLATINUM",
            "rank": "IV",
            "summonerId": "encryptedSummonerId",
            "summonerName": "티모석",
            "leaguePoints": 57,
            "wins": 7,
            "losses": 5,
            "veteran": false,
            "inactive": false,
            "freshBlood": false,
            "hotStreak": false
        }
    ]
```

Get all the league entries

```py
@lol.get("/lol/league/v4/entries/{queue}/{tier}/{division}")
async def all_league_entries(page: int = 1):

    # RESPONSE BODY
    return [
        {
            "leagueId": "encryptedLeagueID",
            "queueType": "RANKED_FLEX_SR",
            "tier": "PLATINUM",
            "rank": "IV",
            "summonerId": "encryptedSummonerID",
            "summonerName": "티모석",
            "leaguePoints": 4,
            "wins": 5,
            "losses": 3,
            "veteran": false,
            "inactive": false,
            "freshBlood": false,
            "hotStreak": false
        },
    ]
```

Get the grandmaster league of a specific queue

```py
@lol.get("/lol/league/v4/grandmasterleagues/by-queue/{queue}")

    # RESPONSE BODY
    return {
        "tier": "GRANDMASTER",
        "leagueId": "encryptedLeagueId",
        "queue": "RANKED_SOLO_5x5",
        "name": "Yorick's Duelists",
        "entries": [
            {
                "summonerId": "encryptedSummonerID",
                "summonerName": "역천괴",
                "leaguePoints": 822,
                "rank": "I",
                "wins": 93,
                "losses": 53,
                "veteran": false,
                "inactive": false,
                "freshBlood": true,
                "hotStreak": false
            },
        ]
    }
```

Get the master league for given queue

```py
@lol.get("/lol/league/v4/grandmasterleagues/by-queue/{queue}")

    # RESPONSE BODY
    return {
        "tier": "MASTER",
        "leagueId": "encryptedLeagueId",
        "queue": "RANKED_SOLO_5x5",
        "name": "Irelia's Blackguards",
        "entries": [
            {
                "summonerId": "encryptedSummonerId",
                "summonerName": "The shy",
                "leaguePoints": 439,
                "rank": "I",
                "wins": 61,
                "losses": 29,
                "veteran": false,
                "inactive": false,
                "freshBlood": false,
                "hotStreak": false
            },
        ]
    }
```

Get league with given ID, including inactive entries

```py
@lol.get("/lol/league/v4/grandmasterleagues/by-queue/{queue}")

    # RESPONSE BODY
    return {
        "tier": "PLATINUM",
        "leagueId": "3d0143fd-20af-44d0-9d2c-fff8305ef90b",
        "queue": "RANKED_FLEX_SR",
        "name": "Wukong's Soldiers",
        "entries": [
            {
                "summonerId": "dtoD2b5WV3G4xBRs5Lg-Yxh_rVItyn9mgRAxzdBk37-aS-A",
                "summonerName": "티모석",
                "leaguePoints": 57,
                "rank": "IV",
                "wins": 7,
                "losses": 5,
                "veteran": false,
                "inactive": false,
                "freshBlood": false,
                "hotStreak": false
            },
        ]
    }
```

### LOL-CHALLENGES-V1

List of all basic challenge configuration information  
includes all translations for names and descriptions

```py
@lol.get("/lol/challenges/v1/challenges/config")

    # RESPONSE BODY
    return [
        {
            "id": 202301,
            "localizedNames": {
                "ar_AE": {
                    "description": "",
                    "name": "",
                    "shortDescription": ""
                },
                "cs_CZ": {
                    "description": "Vyhrávej zápasy s alespoň osmi zářezy, aniž by tě někdo zabil.",
                    "name": "Nesmrtelný",
                    "shortDescription": "Vyhraj zápas s alespoň osmi zářezy, aniž by tě někdo zabil."
                },
                "de_DE": {
                    "description": "Gewinne Spiele mit mindestens acht Kills, ohne zu sterben.",
                    "name": "Unsterblich",
                    "shortDescription": "Gewinne ein Spiel mit mindestens acht Kills, ohne zu sterben."
                },
                "el_GR": {
                    "description": "Κερδίστε παιχνίδια με οκτώ ή περισσότερες εκτελέσεις, χωρίς θανάτους",
                    "name": "Αθάνατος",
                    "shortDescription": "Κερδίστε ένα παιχνίδι με περισσότερες από 8 εκτελέσεις χωρίς θανάτους"
                },
                "en_AU": {
                    "description": "Win games with eight or more kills without dying",
                    "name": "Immortal",
                    "shortDescription": "Win a game with more than eight kills without dying"
                },
                "en_GB": {
                    "description": "Win games with eight or more kills without dying",
                    "name": "Immortal",
                    "shortDescription": "Win a game with more than eight kills without dying"
                },
                "en_PH": {
                    "description": "Win games with eight or more kills without dying",
                    "name": "Immortal",
                    "shortDescription": "Win a game with more than eight kills without dying"
                },
                "en_SG": {
                    "description": "Win games with eight or more kills without dying",
                    "name": "Immortal",
                    "shortDescription": "Win a game with more than eight kills without dying"
                },
                "en_US": {
                    "description": "Win games with eight or more kills without dying",
                    "name": "Immortal",
                    "shortDescription": "Win a game with more than eight kills without dying"
                },
                "es_AR": {
                    "description": "Gana partidas con ocho o más asesinatos sin morir.",
                    "name": "Inmortal",
                    "shortDescription": "Gana una partida con más de ocho asesinatos sin morir."
                },
                "es_ES": {
                    "description": "Gana partidas con ocho o más asesinatos sin morir.",
                    "name": "Inmortal",
                    "shortDescription": "Gana una partida con más de 8 asesinatos sin morir."
                },
                "es_MX": {
                    "description": "Gana partidas con ocho o más asesinatos sin morir.",
                    "name": "Inmortal",
                    "shortDescription": "Gana una partida con más de ocho asesinatos sin morir."
                },
                "fr_FR": {
                    "description": "Gagnez des parties en ayant réussi au moins 8 éliminations sans mourir.",
                    "name": "Immortalité",
                    "shortDescription": "Gagnez une partie en ayant réussi plus de 8 éliminations sans mourir."
                },
                "hu_HU": {
                    "description": "Nyerj meg játékokat úgy, hogy legalább nyolc gyilkosságot szerzel, és egyszer sem halsz meg",
                    "name": "Halhatatlan",
                    "shortDescription": "Nyerj meg egy játékot legalább 8 gyilkossággal meghalás nélkül"
                },
                "it_IT": {
                    "description": "Vinci partite con otto o più uccisioni senza morire",
                    "name": "Immortale",
                    "shortDescription": "Vinci una partita con più di otto uccisioni senza morire"
                },
                "ja_JP": {
                    "description": "デスせずに8キル以上を獲得して試合に勝利",
                    "name": "不死",
                    "shortDescription": "デスせずに8キル以上を奪って試合に勝利"
                },
                "ko_KR": {
                    "description": "죽지 않고 8킬 이상 달성하며 게임 승리",
                    "name": "불멸",
                    "shortDescription": "죽지 않고 8킬 이상 달성하며 게임 승리"
                },
                "pl_PL": {
                    "description": "Wygraj grę, uzyskując 8 lub więcej zabójstw bez umierania.",
                    "name": "Nieśmiertelny",
                    "shortDescription": "Wygraj grę, zdobywając więcej niż 8 zabójstw bez umierania."
                },
                "pt_BR": {
                    "description": "Vença partidas com 8 ou mais abates sem morrer",
                    "name": "Imortal",
                    "shortDescription": "Vença uma partida com mais de oito abates sem morrer"
                },
                "ro_RO": {
                    "description": "Câștigă meciuri cu cel puțin opt asasinate fără să mori",
                    "name": "Nemuritor",
                    "shortDescription": "Câștigă un meci în care să ai mai mult de opt asasinate fără să mori"
                },
                "ru_RU": {
                    "description": "Победите, совершив как минимум 8 убийств и ни разу не умерев",
                    "name": "Бессмертный",
                    "shortDescription": "Победите в играх, совершая 8+ убийств и не умирая"
                },
                "th_TH": {
                    "description": "ชนะด้วยคะแนนสังหาร 8 คะแนนขึั้นไปโดยไม่ตาย",
                    "name": "Immortal",
                    "shortDescription": "ชนะ 1 เกมพร้อมคะแนนสังหาร 8 คะแนนขึ้นไปโดยไม่ตาย"
                },
                "tr_TR": {
                    "description": "Katledilmeden sekiz veya daha fazla skor aldığın oyunlar kazan.",
                    "name": "Ölümsüz",
                    "shortDescription": "Katledilmeden en az 8 skor aldığın bir oyun kazan."
                },
                "vi_VN": {
                    "description": "Thắng trận khi hạ gục trên 8 mạng mà không bị hạ gục",
                    "name": "Bất Diệt",
                    "shortDescription": "Thắng một trận với trên 8 mạng hạ gục mà không chết"
                },
                "zh_CN": {
                    "description": "赢得对局，且你拿到了8次或以上的击杀并一次都不死",
                    "name": "完美超神",
                    "shortDescription": "赢得一场对局且拿到8+次击杀并一次不死"
                },
                "zh_MY": {
                    "description": "在取得 8 个或以上击杀且未死亡过的情况下赢得对战",
                    "name": "永垂不朽",
                    "shortDescription": "在取得 8 个或以上击杀且未死亡过的情况下赢得对战"
                },
                "zh_TW": {
                    "description": "在取得 8 次以上擊殺且未死亡過的情況下贏得對戰",
                    "name": "永垂不朽",
                    "shortDescription": "在取得 8 個以上擊殺且未死亡過的情況下贏得對戰"
                }
            },
            "state": "ENABLED",
            "leaderboard": false,
            "thresholds": {
                "GOLD": 2,
                "MASTER": 10,
                "DIAMOND": 5,
                "PLATINUM": 3,
                "SILVER": 1
            }
        },
    ]
```

Map of level to percentile of players who have achieved it  
keys: ChallengeId -> Season -> Level -> percentile of players who achieved it

```py
@lol.get("/lol/challenges/v1/challenges/percentiles")

    # RESPONSE BODY
    return {
        "202301": {
            "SILVER": 0.344,
            "DIAMOND": 0.101,
            "MASTER": 0.033,
            "PLATINUM": 0.172,
            "GOLD": 0.235,
            "CHALLENGER": 0,
            "BRONZE": 0,
            "GRANDMASTER": 0,
            "IRON": 0
        },
    }
```

Get challenge configuration  
REST

```py
@lol.get("/lol/challenges/v1/challenges/{challengeId}/config")

    # RESPONSE BODY
    return  {
        "id": 202301,
        "localizedNames": {
            "ar_AE": {
                "description": "",
                "name": "",
                "shortDescription": ""
            },
            "cs_CZ": {
                "description": "Vyhrávej zápasy s alespoň osmi zářezy, aniž by tě někdo zabil.",
                "name": "Nesmrtelný",
                "shortDescription": "Vyhraj zápas s alespoň osmi zářezy, aniž by tě někdo zabil."
            },
            "de_DE": {
                "description": "Gewinne Spiele mit mindestens acht Kills, ohne zu sterben.",
                "name": "Unsterblich",
                "shortDescription": "Gewinne ein Spiel mit mindestens acht Kills, ohne zu sterben."
            },
            "el_GR": {
                "description": "Κερδίστε παιχνίδια με οκτώ ή περισσότερες εκτελέσεις, χωρίς θανάτους",
                "name": "Αθάνατος",
                "shortDescription": "Κερδίστε ένα παιχνίδι με περισσότερες από 8 εκτελέσεις χωρίς θανάτους"
            },
            "en_AU": {
                "description": "Win games with eight or more kills without dying",
                "name": "Immortal",
                "shortDescription": "Win a game with more than eight kills without dying"
            },
            "en_GB": {
                "description": "Win games with eight or more kills without dying",
                "name": "Immortal",
                "shortDescription": "Win a game with more than eight kills without dying"
            },
            "en_PH": {
                "description": "Win games with eight or more kills without dying",
                "name": "Immortal",
                "shortDescription": "Win a game with more than eight kills without dying"
            },
            "en_SG": {
                "description": "Win games with eight or more kills without dying",
                "name": "Immortal",
                "shortDescription": "Win a game with more than eight kills without dying"
            },
            "en_US": {
                "description": "Win games with eight or more kills without dying",
                "name": "Immortal",
                "shortDescription": "Win a game with more than eight kills without dying"
            },
            "es_AR": {
                "description": "Gana partidas con ocho o más asesinatos sin morir.",
                "name": "Inmortal",
                "shortDescription": "Gana una partida con más de ocho asesinatos sin morir."
            },
            "es_ES": {
                "description": "Gana partidas con ocho o más asesinatos sin morir.",
                "name": "Inmortal",
                "shortDescription": "Gana una partida con más de 8 asesinatos sin morir."
            },
            "es_MX": {
                "description": "Gana partidas con ocho o más asesinatos sin morir.",
                "name": "Inmortal",
                "shortDescription": "Gana una partida con más de ocho asesinatos sin morir."
            },
            "fr_FR": {
                "description": "Gagnez des parties en ayant réussi au moins 8 éliminations sans mourir.",
                "name": "Immortalité",
                "shortDescription": "Gagnez une partie en ayant réussi plus de 8 éliminations sans mourir."
            },
            "hu_HU": {
                "description": "Nyerj meg játékokat úgy, hogy legalább nyolc gyilkosságot szerzel, és egyszer sem halsz meg",
                "name": "Halhatatlan",
                "shortDescription": "Nyerj meg egy játékot legalább 8 gyilkossággal meghalás nélkül"
            },
            "it_IT": {
                "description": "Vinci partite con otto o più uccisioni senza morire",
                "name": "Immortale",
                "shortDescription": "Vinci una partita con più di otto uccisioni senza morire"
            },
            "ja_JP": {
                "description": "デスせずに8キル以上を獲得して試合に勝利",
                "name": "不死",
                "shortDescription": "デスせずに8キル以上を奪って試合に勝利"
            },
            "ko_KR": {
                "description": "죽지 않고 8킬 이상 달성하며 게임 승리",
                "name": "불멸",
                "shortDescription": "죽지 않고 8킬 이상 달성하며 게임 승리"
            },
            "pl_PL": {
                "description": "Wygraj grę, uzyskując 8 lub więcej zabójstw bez umierania.",
                "name": "Nieśmiertelny",
                "shortDescription": "Wygraj grę, zdobywając więcej niż 8 zabójstw bez umierania."
            },
            "pt_BR": {
                "description": "Vença partidas com 8 ou mais abates sem morrer",
                "name": "Imortal",
                "shortDescription": "Vença uma partida com mais de oito abates sem morrer"
            },
            "ro_RO": {
                "description": "Câștigă meciuri cu cel puțin opt asasinate fără să mori",
                "name": "Nemuritor",
                "shortDescription": "Câștigă un meci în care să ai mai mult de opt asasinate fără să mori"
            },
            "ru_RU": {
                "description": "Победите, совершив как минимум 8 убийств и ни разу не умерев",
                "name": "Бессмертный",
                "shortDescription": "Победите в играх, совершая 8+ убийств и не умирая"
            },
            "th_TH": {
                "description": "ชนะด้วยคะแนนสังหาร 8 คะแนนขึั้นไปโดยไม่ตาย",
                "name": "Immortal",
                "shortDescription": "ชนะ 1 เกมพร้อมคะแนนสังหาร 8 คะแนนขึ้นไปโดยไม่ตาย"
            },
            "tr_TR": {
                "description": "Katledilmeden sekiz veya daha fazla skor aldığın oyunlar kazan.",
                "name": "Ölümsüz",
                "shortDescription": "Katledilmeden en az 8 skor aldığın bir oyun kazan."
            },
            "vi_VN": {
                "description": "Thắng trận khi hạ gục trên 8 mạng mà không bị hạ gục",
                "name": "Bất Diệt",
                "shortDescription": "Thắng một trận với trên 8 mạng hạ gục mà không chết"
            },
            "zh_CN": {
                "description": "赢得对局，且你拿到了8次或以上的击杀并一次都不死",
                "name": "完美超神",
                "shortDescription": "赢得一场对局且拿到8+次击杀并一次不死"
            },
            "zh_MY": {
                "description": "在取得 8 个或以上击杀且未死亡过的情况下赢得对战",
                "name": "永垂不朽",
                "shortDescription": "在取得 8 个或以上击杀且未死亡过的情况下赢得对战"
            },
            "zh_TW": {
                "description": "在取得 8 次以上擊殺且未死亡過的情況下贏得對戰",
                "name": "永垂不朽",
                "shortDescription": "在取得 8 個以上擊殺且未死亡過的情況下贏得對戰"
            }
        },
        "state": "ENABLED",
        "leaderboard": false,
        "thresholds": {
            "DIAMOND": 5,
            "GOLD": 2,
            "SILVER": 1,
            "MASTER": 10,
            "PLATINUM": 3
        }
    }
```

Return top players for each level. Level must be MASTER, GRANDMASTER or CHALLENGER

```py
@lol.get("/lol/challenges/v1/challenges/{challengeId}/leaderboards/by-level/{level}")
async def challenges_top_players(limit: int | None = None):

    # RESPONSE BODY
    return [
        {
            "position": 1,
            "puuid": "encryptedPUUID,
            "value": 29007
        },
    ]
```

Map of level to percentile of players who have achieved it

```py
@lol.get("/lol/challenges/v1/challenges/{challengeId}/percentilesMap")

    # RESPONSE BODY
    return {
        "PLATINUM": 0.172,
        "GRANDMASTER": 0,
        "IRON": 0,
        "GOLD": 0.235,
        "MASTER": 0.033,
        "CHALLENGER": 0,
        "BRONZE": 0,
        "SILVER": 0.344,
        "DIAMOND": 0.101,
        "NONE": 1
    }
```

Returns player information with list of all progressed challenges  
REST

```py
@lol.get("/lol/challenges/v1/player-data/{puuid}")

    # RESPONSE BODY
    return {
        "totalPoints": {
            "level": "GOLD",
            "current": 7160,
            "max": 28580,
            "percentile": 0.292
        },
        "categoryPoints": {
            "COLLECTION": {
                "level": "GOLD",
                "current": 825,
                "max": 4200,
                "percentile": 0.263
            },
            "VETERANCY": {
                "level": "PLATINUM",
                "current": 1560,
                "max": 5600,
                "percentile": 0.141
            },
            "TEAMWORK": {
                "level": "PLATINUM",
                "current": 2145,
                "max": 7735,
                "percentile": 0.087
            },
            "IMAGINATION": {
                "level": "GOLD",
                "current": 950,
                "max": 4330,
                "percentile": 0.261
            },
            "EXPERTISE": {
                "level": "GOLD",
                "current": 1680,
                "max": 6715,
                "percentile": 0.293
            }
        },
        "challenges": [
            {
                "challengeId": 0,
                "percentile": 0.292,
                "level": "GOLD",
                "value": 7160,
                "achievedTime": 1668528854112
            },
        ],
        "preferences": {
            "bannerAccent": "1",
            "title": "",
            "challengeIds": [],
            "crestBorder": "1",
            "prestigeCrestBorderLevel": 1
        }
    }
```

### LOL-STATUS-V4

Get League of Legends status for the given platform

```py

    return {
        "id": "KR",
        "name": "Korea",
        "locales": [
            "cs_CZ",
            "de_DE",
            "el_GR",
            "en_AU",
            "en_GB",
            "en_PH",
            "en_SG",
            "en_US",
            "es_AR",
            "es_ES",
            "es_MX",
            "fr_FR",
            "hu_HU",
            "it_IT",
            "ja_JP",
            "ko_KR",
            "pl_PL",
            "pt_BR",
            "ro_RO",
            "ru_RU",
            "th_TH",
            "tr_TR",
            "vi_VN",
            "zh_MY",
            "zh_TW"
        ],
        "maintenances": [
            {
                "id": 8168,
                "created_at": "2024-03-04T20:53:43.911056+00:00",
                "updated_at": "2024-03-05T21:00:01.868807+00:00",
                "archive_at": "2024-03-06T02:00:00+00:00",
                "titles": [
                    {
                        "locale": "es_ES",
                        "content": "Notificación de mantenimiento"
                    },
                    {
                        "locale": "it_IT",
                        "content": "Notifica manutenzione"
                    },
                    {
                        "locale": "cs_CZ",
                        "content": "Upozornění o údržbě"
                    },
                    {
                        "locale": "pt_BR",
                        "content": "Notificação de manutenção"
                    },
                    {
                        "locale": "en_AU",
                        "content": "Maintenance Notification"
                    },
                    {
                        "locale": "zh_TW",
                        "content": "維護通知"
                    },
                    {
                        "locale": "ru_RU",
                        "content": "Уведомление о техническом обслуживании"
                    },
                    {
                        "locale": "ro_RO",
                        "content": "Notificare cu privire la lucrări de întreținere"
                    },
                    {
                        "locale": "vi_VN",
                        "content": "Thông Báo Bảo Trì"
                    },
                    {
                        "locale": "en_PH",
                        "content": "Maintenance Notification"
                    },
                    {
                        "locale": "en_GB",
                        "content": "Maintenance Notification"
                    },
                    {
                        "locale": "fr_FR",
                        "content": "Notifications de maintenance"
                    },
                    {
                        "locale": "en_SG",
                        "content": "Maintenance Notification"
                    },
                    {
                        "locale": "ja_JP",
                        "content": "メンテナンス通知"
                    },
                    {
                        "locale": "es_MX",
                        "content": "Notificación de mantenimiento."
                    },
                    {
                        "locale": "tr_TR",
                        "content": "Bakım Bildirimi"
                    },
                    {
                        "locale": "el_GR",
                        "content": "Ειδοποίηση συντήρησης"
                    },
                    {
                        "locale": "es_AR",
                        "content": "Notificación de mantenimiento."
                    },
                    {
                        "locale": "ko_KR",
                        "content": "점검 안내"
                    },
                    {
                        "locale": "hu_HU",
                        "content": "Karbantartással kapcsolatos értesítés"
                    },
                    {
                        "locale": "de_DE",
                        "content": "Benachrichtigung zu Wartungsarbeiten"
                    },
                    {
                        "locale": "en_US",
                        "content": "Maintenance Notification"
                    },
                    {
                        "locale": "th_TH",
                        "content": "แจ้งเตือนการปิดปรับปรุงระบบ"
                    },
                    {
                        "locale": "pl_PL",
                        "content": "Powiadomienie o przerwie technicznej"
                    },
                    {
                        "locale": "zh_MY",
                        "content": "维护通知"
                    }
                ],
                "updates": [
                    {
                        "id": 14236,
                        "created_at": "2024-03-04T20:53:43.949463+00:00",
                        "updated_at": "2024-03-04T21:00:00+00:00",
                        "publish": true,
                        "author": "Riot Games",
                        "translations": [
                            {
                                "locale": "es_ES",
                                "content": "Las colas clasificatorias estarán desactivadas a partir de las 05/03/2024 19:30 GMT en preparación de la versión 14.5. A las 05/03/2024 21:00 GMT los servidores se desactivarán, las partidas en curso terminarán en un empate y las estadísticas no se guardarán. Calculamos que los campos de batalla no estará disponibles durante 3 horas aproximadamente."
                            },
                            {
                                "locale": "it_IT",
                                "content": "Le code classificate saranno disabilitate a partire dalle ore 05/03/2024 19:30 GMT in preparazione alla patch 14.5. Alle ore 05/03/2024 21:00 GMT i server verranno chiusi e tutte le partite in corso finiranno con un pareggio. Le statistiche non verranno registrate. Secondo le nostre stime, non sarà possibile accedere al gioco per circa 3 ore."
                            },
                            {
                                "locale": "cs_CZ",
                                "content": "Od 05/03/2024 20:30 CET nebudou k dispozici hodnocené fronty z důvodu přípravy na aktualizaci 14.5. V 05/03/2024 22:00 CET dojde k vypnutí serverů. Všechny aktuálně probíhající zápasy skončí remízou a jejich statistiky se neuloží. Očekáváme, že bojiště nebudou k dispozici po dobu 3 hodin."
                            },
                            {
                                "locale": "pt_BR",
                                "content": "A partir das 05/03/2024 16:30 GMT-03, as Filas Ranqueadas serão desativadas em preparação para a Atualização 14.5. Às 05/03/2024 18:00 GMT-03, os servidores serão desativados e todas as partidas em andamento acabarão empatadas, sem registro das estatísticas. Estimamos que os campos de batalha estejam indisponíveis por 3 horas."
                            },
                            {
                                "locale": "en_AU",
                                "content": "Starting at 06/03/2024 06:30 AEDT Ranked Queues will be disabled in preparation for patch 14.5. At 06/03/2024 08:00 AEDT the servers will be shut down and all games currently in progress will end in a draw and stats will not be recorded. We estimate the battlegrounds will be unavailable for 3 hours."
                            },
                            {
                                "locale": "zh_TW",
                                "content": "為了進行14.5版本更新，積分配對將於2024/03/06 03:30 GMT+08關閉。從2024/03/06 05:00 GMT+08開始，伺服器將關閉，正在進行中的對戰會以平手結束，且對戰數據不會被記錄下來。戰場預計關閉3小時。"
                            },
                            {
                                "locale": "ru_RU",
                                "content": "05/03/2024 22:30 MSK ранговые очереди будут отключены для подготовки к обновлению 14.5. В 06/03/2024 00:00 MSK серверы будут отключены и все текущие игры автоматически закончатся ничьей. Мы ожидаем, что поля боя будут недоступны в течение 3 часов."
                            },
                            {
                                "locale": "ro_RO",
                                "content": "Începând cu 05/03/2024 20:30 CET, listele de așteptare ranked vor fi dezactivate în așteptarea patch-ului 14.5. La 05/03/2024 22:00 CET, serverele vor fi închise și toate jocurile în desfășurare în acel moment se vor încheia cu un egal, iar rezultatele nu vor fi înregistrate. Estimăm că jocul nu va fi disponibil timp de 3 ore."
                            },
                            {
                                "locale": "vi_VN",
                                "content": "Vào lúc 02:30 GMT+07 06/03/2024, Hàng chờ Đấu xếp hạng sẽ bị vô hiệu hóa để chuẩn bị cho bản cập nhật 14.5. Vào lúc 04:00 GMT+07 06/03/2024, máy chủ sẽ đóng lại, tất cả các trận đấu đang diễn ra sẽ kết thúc với kết quả hòa và thông số sẽ không được ghi vào hệ thống. Chiến trường sẽ tạm dừng hoạt động trong khoảng 3 giờ."
                            },
                            {
                                "locale": "en_PH",
                                "content": "Starting at 03:30 GMT+08 06/03/2024 Ranked Queues will be disabled in preparation for patch 14.5. At 05:00 GMT+08 06/03/2024 the servers will be shut down and all games currently in progress will end in a draw and stats will not be recorded. We estimate the battlegrounds will be unavailable for 3 hours."
                            },
                            {
                                "locale": "en_GB",
                                "content": "Starting at 05/03/2024 19:30 GMT Ranked Queues will be disabled in preparation for patch 14.5. At 05/03/2024 21:00 GMT the servers will be shut down and all games currently in progress will end in a draw and stats will not be recorded. We estimate the battlegrounds will be unavailable for 3 hours."
                            },
                            {
                                "locale": "fr_FR",
                                "content": "À partir de 05/03/2024 19:30 GMT, les files classées seront désactivées en prévision du patch 14.5. À 05/03/2024 21:00 GMT, les serveurs seront désactivés et toutes les parties en cours se termineront par un match nul. Les statistiques de ces parties ne seront pas enregistrées. Les champs de bataille seront indisponibles pendant approximativement 3 heures."
                            },
                            {
                                "locale": "en_SG",
                                "content": "Starting at 03:30 GMT+08 06/03/2024 Ranked Queues will be disabled in preparation for patch 14.5. At 05:00 GMT+08 06/03/2024 the servers will be shut down and all games currently in progress will end in a draw and stats will not be recorded. We estimate the battlegrounds will be unavailable for 3 hours."
                            },
                            {
                                "locale": "ja_JP",
                                "content": "パッチ 14.5アップデートのため、2024/03/06 04:30 JST よりランク戦を開始できなくなります。全サーバーで進行中のゲームは2024/03/06 06:00 JSTに停止されます。試合結果は引き分けとなり、記録されませんのでご注意ください。作業はおよそ3時間で完了予定です。"
                            },
                            {
                                "locale": "es_MX",
                                "content": "A partir de las 05/03/2024 13:30 GMT-06, desactivaremos las colas clasificatorias para preparar la versión 14.5. Apagaremos los servidores a las 05/03/2024 15:00 GMT-06. Todas las partidas en curso terminarán en empate y no se guardarán datos de ellas. Estimamos que los campos de batalla no estarán disponibles por 3 horas."
                            },
                            {
                                "locale": "tr_TR",
                                "content": "14.5 Yaması'nın çıkış hazırlıkları nedeniyle dereceli sıralar 05/03/2024 22:30 GMT+03 itibarı ile devre dışı bırakılacaktır. Sunucular 06/03/2024 00:00 GMT+03 olduğunda kapatılacak, o esnada devam eden bütün karşılaşmalar berabere sonuçlanacak ve kayıtları tutulmayacaktır. Yama çalışmaları nedeniyle savaş meydanlarının 3 saat kadar kapalı kalacağı tahmin edilmekte."
                            },
                            {
                                "locale": "el_GR",
                                "content": "Οι ουρές Αγώνων Κατάταξης δεν θα είναι διαθέσιμες από τις 05/03/2024 20:30 CET λόγω της εγκατάστασης της Ενημέρωσης 14.5. Στις 05/03/2024 22:00 CET, οι σέρβερ θα απενεργοποιηθούν και όλα τα παιχνίδια που είναι σε εξέλιξη θα λήξουν ισόπαλα, χωρίς καταγραφή στατιστικών. Υπολογίζουμε ότι οι Αγώνες Κατάταξης θα παραμείνουν εκτός λειτουργίας για 3 ώρες."
                            },
                            {
                                "locale": "es_AR",
                                "content": "A partir de las 05/03/2024 16:30 GMT-03, desactivaremos las colas clasificatorias para preparar la versión 14.5. Apagaremos los servidores a las 05/03/2024 18:00 GMT-03. Todas las partidas en curso terminarán en empate y no se guardarán datos de ellas. Estimamos que los campos de batalla no estarán disponibles por 3 horas."
                            },
                            {
                                "locale": "ko_KR",
                                "content": "14.5 패치 준비를 위해 랭크 게임이 2024/03/06 04:30 KST 부터 비활성화 됩니다. 서버는 2024/03/06 06:00 KST에 비활성화되며, 이를 기준으로 진행 중인 모든 게임은 즉시 종료되고 대전기록에 저장되지 않습니다. 점검은 약 5시간 동안 진행될 예정입니다."
                            },
                            {
                                "locale": "hu_HU",
                                "content": "A rangsorolt várólisták az új frissítés (14.5) előkészületei miatt a következő időponttól kezdődően nem lesznek elérhetők: 05/03/2024 20:30 CET. A következő időpontban a szerverek leállnak: 05/03/2024 22:00 CET. Minden folyamatban lévő meccs döntetlen eredménnyel zárul, a statisztikák pedig nem lesznek rögzítve. A csatamezők előreláthatólag 3 órán át nem lesznek elérhetők."
                            },
                            {
                                "locale": "de_DE",
                                "content": "Ab 05/03/2024 19:30 GMT ist die Rangliste in Vorbereitung auf Patch 14.5 deaktiviert. Um 05/03/2024 21:00 GMT werden die Server heruntergefahren. Alle Spiele, die zu diesem Zeitpunkt noch im Gange sind, werden unentschieden beendet und nicht gewertet. Wir schätzen, dass die Schlachtfelder für 3 Stunden nicht verfügbar sein werden."
                            },
                            {
                                "locale": "en_US",
                                "content": "Starting at 03/05/2024 11:30 PST Ranked Queues will be disabled in preparation for patch 14.5. At 03/05/2024 13:00 PST the servers will be shut down and all games currently in progress will end in a draw and stats will not be recorded. We estimate the battlegrounds will be unavailable for 3 hours."
                            },
                            {
                                "locale": "th_TH",
                                "content": "เริ่มตั้งแต่เวลา 02:30 GMT+07 06/03/2024 คิวเกมจัดอันดับจะถูกปิดการใช้งานเพื่อเตรียมพร้อมสำหรับแพตช์ 14.5 ในเวลา 04:00 GMT+07 06/03/2024 เซิร์ฟเวอร์จะปิดปรับปรุงและเกมทั้งหมดที่กำลังดำเนินการอยู่จะจบลงเป็นผลเสมอ และสถิติต่าง ๆ จะไม่ถูกบันทึก เราคาดการณ์ว่าสนามต่อสู้จะไม่สามารถใช้งานได้เป็นเวลา 3 ชั่วโมง"
                            },
                            {
                                "locale": "pl_PL",
                                "content": "Kolejki rankingowe zostaną wyłączone o 05/03/2024 20:30 CET w przygotowaniu na patcha 14.5. O 05/03/2024 22:00 CET serwery zostaną wyłączone. Wszystkie gry, które będą wtedy trwały, zakończą się remisem, a statystyki z nich nie zostaną zapisane. Oceniamy, że pola bitew będą niedostępne przez 3 godziny."
                            },
                            {
                                "locale": "zh_MY",
                                "content": "从03:30 GMT+08 06/03/2024开始，积分赛队列将暂时停用，以便为14.5版本更新进行准备。在05:00 GMT+08 06/03/2024，服务器将关闭，所有正在进行的对战将以平局状态结束，统计数据不会纳入计算。根据预定计划，游戏战场将关闭3小时。"
                            }
                        ],
                        "publish_locations": [
                            "riotstatus"
                        ]
                    },
                    {
                        "id": 14237,
                        "created_at": "2024-03-04T20:53:43.959486+00:00",
                        "updated_at": "2024-03-05T18:00:00+00:00",
                        "publish": true,
                        "author": "Riot Games",
                        "translations": [
                            {
                                "locale": "es_ES",
                                "content": "A las 05/03/2024 19:30 GMT, las colas clasificatorias se desactivarán en preparación de la nueva versión, que se lanzará a las 05/03/2024 21:00 GMT."
                            },
                            {
                                "locale": "it_IT",
                                "content": "Alle ore 05/03/2024 19:30 GMT, le code classificate verranno disattivate in preparazione al rilascio della patch delle ore 05/03/2024 21:00 GMT."
                            },
                            {
                                "locale": "cs_CZ",
                                "content": "V 05/03/2024 20:30 CET budou hodnocené fronty deaktivovány z důvodu přípravy na vydání aktualizace, ke kterému dojde v 05/03/2024 22:00 CET."
                            },
                            {
                                "locale": "pt_BR",
                                "content": "Às 05/03/2024 16:30 GMT-03, as Filas Ranqueadas serão desativadas em preparação para a implementação da Atualização que ocorrerá em 05/03/2024 18:00 GMT-03."
                            },
                            {
                                "locale": "en_AU",
                                "content": "At 06/03/2024 06:30 AEDT, Ranked Queues will be turned off in preparation for the patch deployment that will occur at 06/03/2024 08:00 AEDT."
                            },
                            {
                                "locale": "zh_TW",
                                "content": "由於將在2024/03/06 05:00 GMT+08進行版本更新，積分模式將於2024/03/06 03:30 GMT+08暫時關閉。"
                            },
                            {
                                "locale": "ru_RU",
                                "content": "В 05/03/2024 22:30 MSK ранговые очереди будут отключены для подготовки к обновлению, которое начнется в 06/03/2024 00:00 MSK."
                            },
                            {
                                "locale": "ro_RO",
                                "content": "La 05/03/2024 20:30 CET, listele de așteptare ranked vor fi dezactivate în așteptarea lansării patch-ului, care va avea loc la 05/03/2024 22:00 CET."
                            },
                            {
                                "locale": "vi_VN",
                                "content": "Vào lúc 02:30 GMT+07 06/03/2024, Hàng chờ Đấu xếp hạng sẽ bị vô hiệu hóa để chuẩn bị triển khai bản cập nhật vào lúc 04:00 GMT+07 06/03/2024."
                            },
                            {
                                "locale": "en_PH",
                                "content": "At 03:30 GMT+08 06/03/2024, Ranked Queues will be turned off in preparation for the patch deployment that will occur at 05:00 GMT+08 06/03/2024."
                            },
                            {
                                "locale": "en_GB",
                                "content": "At 05/03/2024 19:30 GMT, Ranked Queues will be turned off in preparation for the patch deployment that will occur at 05/03/2024 21:00 GMT."
                            },
                            {
                                "locale": "fr_FR",
                                "content": "À 05/03/2024 19:30 GMT, les files classées seront désactivées en prévision du déploiement d'un patch qui aura lieu à 05/03/2024 21:00 GMT."
                            },
                            {
                                "locale": "en_SG",
                                "content": "At 03:30 GMT+08 06/03/2024, Ranked Queues will be turned off in preparation for the patch deployment that will occur at 05:00 GMT+08 06/03/2024."
                            },
                            {
                                "locale": "ja_JP",
                                "content": "パッチアップデートのため、2024/03/06 04:30 JST よりランク戦を開始できなくなります。アップデートは 2024/03/06 06:00 JST に開始予定です。"
                            },
                            {
                                "locale": "es_MX",
                                "content": "A las 05/03/2024 13:30 GMT-06, desactivaremos las colas clasificatorias para preparar la actualización que iniciará a las 05/03/2024 15:00 GMT-06."
                            },
                            {
                                "locale": "tr_TR",
                                "content": "Saat 06/03/2024 00:00 GMT+03 itibarı ile başlayacak yama güncellemesinin hazırlıkları nedeniyle, dereceli sıralar 05/03/2024 22:30 GMT+03 sonrası devre dışı bırakılacaktır."
                            },
                            {
                                "locale": "el_GR",
                                "content": "Στις 05/03/2024 20:30 CET, οι ουρές αναμονής αγώνων κατάταξης θα απενεργοποιηθούν λόγω προετοιμασίας για την εγκατάσταση ενημέρωσης που θα πραγματοποιηθεί στις 05/03/2024 22:00 CET."
                            },
                            {
                                "locale": "es_AR",
                                "content": "A las 05/03/2024 16:30 GMT-03, desactivaremos las colas clasificatorias para preparar la actualización que iniciará a las 05/03/2024 18:00 GMT-03."
                            },
                            {
                                "locale": "ko_KR",
                                "content": "패치 준비로 인해 2024/03/06 04:30 KST부터 랭크 게임이 비활성화 됩니다. 패치는 2024/03/06 06:00 KST부터 시작될 예정입니다."
                            },
                            {
                                "locale": "hu_HU",
                                "content": "A rangsorolt várólisták a következő időponttól nem fognak működni, mert egy frissítés telepítésének előkészületei zajlanak a háttérben: 05/03/2024 20:30 CET. A telepítés időpontja: 05/03/2024 22:00 CET."
                            },
                            {
                                "locale": "de_DE",
                                "content": "Um 05/03/2024 19:30 GMT wird die Rangliste in Vorbereitung auf die Aktualisierung um 05/03/2024 21:00 GMT abgeschaltet."
                            },
                            {
                                "locale": "en_US",
                                "content": "At 03/05/2024 11:30 PST, Ranked Queues will be turned off in preparation for the patch deployment that will occur at 03/05/2024 13:00 PST."
                            },
                            {
                                "locale": "th_TH",
                                "content": "ในเวลา 02:30 GMT+07 06/03/2024 คิวเกมจัดอันดับจะไม่สามารถใช้งานได้เพื่อเตรียมพร้อมสำหรับการปล่อยแพตช์ในเวลา 04:00 GMT+07 06/03/2024"
                            },
                            {
                                "locale": "pl_PL",
                                "content": "O 05/03/2024 20:30 CET kolejki rankingowe zostaną wyłączone ze względu na rozpoczynające się o 05/03/2024 22:00 CET wdrażanie patcha."
                            },
                            {
                                "locale": "zh_MY",
                                "content": "在03:30 GMT+08 06/03/2024，积分赛队列将暂时停用，以便为05:00 GMT+08 06/03/2024开始的版本更新进行准备。"
                            }
                        ],
                        "publish_locations": [
                            "riotstatus",
                            "game",
                            "riotclient"
                        ]
                    },
                    {
                        "id": 14238,
                        "created_at": "2024-03-04T20:53:43.964389+00:00",
                        "updated_at": "2024-03-05T19:30:00+00:00",
                        "publish": true,
                        "author": "Riot Games",
                        "translations": [
                            {
                                "locale": "es_ES",
                                "content": "Las colas clasificatorias estarán desactivadas en preparación de la nueva versión a las 05/03/2024 21:00 GMT."
                            },
                            {
                                "locale": "it_IT",
                                "content": "Al momento le code classificate sono disabilitate in preparazione al rilascio della patch previsto per le ore 05/03/2024 21:00 GMT."
                            },
                            {
                                "locale": "cs_CZ",
                                "content": "Hodnocené fronty jsou deaktivovány z důvody přípravy nadcházející aktualizace, k níž dojde v 05/03/2024 22:00 CET."
                            },
                            {
                                "locale": "pt_BR",
                                "content": "As Filas Ranqueadas estão desativadas em preparação para a próxima Atualização, às 05/03/2024 18:00 GMT-03."
                            },
                            {
                                "locale": "en_AU",
                                "content": "Ranked Queues are disabled in preparation for the upcoming patch at 06/03/2024 08:00 AEDT."
                            },
                            {
                                "locale": "zh_TW",
                                "content": "由於將在2024/03/06 05:00 GMT+08進行版本更新，積分配對功能目前無法使用。"
                            },
                            {
                                "locale": "ru_RU",
                                "content": "Ранговые очереди отключены для подготовки к обновлению, которое начнется в 06/03/2024 00:00 MSK."
                            },
                            {
                                "locale": "ro_RO",
                                "content": "Listele de așteptare ranked sunt dezactivate până la 05/03/2024 22:00 CET, în așteptarea patch-ului următor."
                            },
                            {
                                "locale": "vi_VN",
                                "content": "Hàng chờ Đấu xếp hạng đã bị vô hiệu hóa để chuẩn bị cho bản cập nhật sắp tới vào lúc 04:00 GMT+07 06/03/2024."
                            },
                            {
                                "locale": "en_PH",
                                "content": "Ranked Queues are disabled in preparation for the upcoming patch at 05:00 GMT+08 06/03/2024."
                            },
                            {
                                "locale": "en_GB",
                                "content": "Ranked Queues are disabled in preparation for the upcoming patch at 05/03/2024 21:00 GMT."
                            },
                            {
                                "locale": "fr_FR",
                                "content": "Les files classées sont désactivées en prévision du patch qui sera déployé à 05/03/2024 21:00 GMT."
                            },
                            {
                                "locale": "en_SG",
                                "content": "Ranked Queues are disabled in preparation for the upcoming patch at 05:00 GMT+08 06/03/2024."
                            },
                            {
                                "locale": "ja_JP",
                                "content": "2024/03/06 06:00 JST から開始予定のパッチアップデートのため、現在ランク戦を停止しています。"
                            },
                            {
                                "locale": "es_MX",
                                "content": "Desactivamos las colas clasificatorias para preparar la actualización que se llevará a cabo a las 05/03/2024 15:00 GMT-06."
                            },
                            {
                                "locale": "tr_TR",
                                "content": "Yeni yama hazırlıkları nedeniyle dereceli sıralar 06/03/2024 00:00 GMT+03 itibarı ile devre dışı bırakıldı."
                            },
                            {
                                "locale": "el_GR",
                                "content": "Οι ουρές αναμονής αγώνων κατάταξης είναι απενεργοποιημένες λόγω προετοιμασίας για την εγκατάσταση ενημέρωσης που θα πραγματοποιηθεί στις 05/03/2024 22:00 CET."
                            },
                            {
                                "locale": "es_AR",
                                "content": "Desactivamos las colas clasificatorias para preparar la actualización que se llevará a cabo a las 05/03/2024 18:00 GMT-03."
                            },
                            {
                                "locale": "ko_KR",
                                "content": "2024/03/06 06:00 KST부터 예정된 점검 준비로 인해 랭크 게임이 비활성화 되었습니다. 양해 부탁드립니다."
                            },
                            {
                                "locale": "hu_HU",
                                "content": "A rangsorolt várólisták jelenleg nem érhetők el, mert egy frissítés előkészületei zajlanak a háttérben. A frissítés időpontja: 05/03/2024 22:00 CET."
                            },
                            {
                                "locale": "de_DE",
                                "content": "Die Rangliste wird in Vorbereitung auf den anstehenden Patch um 05/03/2024 21:00 GMT deaktiviert."
                            },
                            {
                                "locale": "en_US",
                                "content": "Ranked Queues are disabled in preparation for the upcoming patch at 03/05/2024 13:00 PST."
                            },
                            {
                                "locale": "th_TH",
                                "content": "คิวเกมจัดอันดับไม่สามารถใช้งานได้ในขณะนี้เพื่อเตรียมพร้อมสำหรับแพตช์ถัดไปในเวลา 04:00 GMT+07 06/03/2024"
                            },
                            {
                                "locale": "pl_PL",
                                "content": "Kolejki rankingowe zostały wyłączone ze względu na patcha, którego wdrażanie rozpocznie się o 05/03/2024 22:00 CET."
                            },
                            {
                                "locale": "zh_MY",
                                "content": "为了针对05:00 GMT+08 06/03/2024的版本更新进行准备，目前积分赛队列已停用。"
                            }
                        ],
                        "publish_locations": [
                            "riotstatus",
                            "game",
                            "riotclient"
                        ]
                    },
                    {
                        "id": 14239,
                        "created_at": "2024-03-04T20:53:43.970091+00:00",
                        "updated_at": "2024-03-05T21:00:00+00:00",
                        "publish": true,
                        "author": "Riot Games",
                        "translations": [
                            {
                                "locale": "es_ES",
                                "content": "El juego no estará disponible durante un tiempo mientras llevamos a cabo tareas de mantenimiento. Esperamos acabar para las 06/03/2024 02:00 GMT."
                            },
                            {
                                "locale": "it_IT",
                                "content": "Durante gli interventi di manutenzione programmati, il gioco sarà temporaneamente non disponibile. La manutenzione dovrebbe terminare entro le ore 06/03/2024 02:00 GMT."
                            },
                            {
                                "locale": "cs_CZ",
                                "content": "Z důvodu plánované údržby je hra dočasně nedostupná. Údržba by měla skončit do 06/03/2024 03:00 CET."
                            },
                            {
                                "locale": "pt_BR",
                                "content": "O jogo está temporariamente indisponível enquanto realizamos uma manutenção programada. Esperamos que a manutenção esteja concluída até 05/03/2024 23:00 GMT-03."
                            },
                            {
                                "locale": "en_AU",
                                "content": "The game is temporarily unavailable while we perform scheduled maintenance, we expect this maintenance to be complete by 06/03/2024 13:00 AEDT."
                            },
                            {
                                "locale": "zh_TW",
                                "content": "我們正在進行例行維護，預計2024/03/06 10:00 GMT+08結束，在此期間遊戲暫不開放遊玩。"
                            },
                            {
                                "locale": "ru_RU",
                                "content": "Игра временно недоступна в связи с проведением запланированных профилактических работ до 06/03/2024 05:00 MSK."
                            },
                            {
                                "locale": "ro_RO",
                                "content": "Jocul este nu este disponibil momentan din cauza unor lucrări de întreținere planificate. Estimăm că vom finaliza lucrările până la 06/03/2024 03:00 CET."
                            },
                            {
                                "locale": "vi_VN",
                                "content": "Tạm thời bạn sẽ không thể vào trò chơi trong khi chúng tôi thực hiện bảo trì theo lịch, dự kiến sẽ hoàn thành vào lúc 09:00 GMT+07 06/03/2024."
                            },
                            {
                                "locale": "en_PH",
                                "content": "The game is temporarily unavailable while we perform scheduled maintenance, we expect this maintenance to be complete by 10:00 GMT+08 06/03/2024."
                            },
                            {
                                "locale": "en_GB",
                                "content": "The game is temporarily unavailable while we perform scheduled maintenance, we expect this maintenance to be complete by 06/03/2024 02:00 GMT."
                            },
                            {
                                "locale": "fr_FR",
                                "content": "Le jeu est temporairement indisponible pour cause de maintenance planifiée. Celle-ci devrait être terminée à 06/03/2024 02:00 GMT."
                            },
                            {
                                "locale": "en_SG",
                                "content": "The game is temporarily unavailable while we perform scheduled maintenance, we expect this maintenance to be complete by 10:00 GMT+08 06/03/2024."
                            },
                            {
                                "locale": "ja_JP",
                                "content": "定期メンテナンスのため現在一時的にゲームをプレイできません。メンテナンスは 2024/03/06 11:00 JST までに完了予定です。"
                            },
                            {
                                "locale": "es_MX",
                                "content": "El juego no estará disponible durante un tiempo mientras llevamos a cabo tareas de mantenimiento. Esperamos terminar a las 05/03/2024 20:00 GMT-06."
                            },
                            {
                                "locale": "tr_TR",
                                "content": "Planlanmış bakım çalışmalarımız sürerken oyun geçici olarak devre dışı kalacak. Bu çalışmaları 06/03/2024 05:00 GMT+03 kadar tamamlamayı planlıyoruz."
                            },
                            {
                                "locale": "el_GR",
                                "content": "Το παιχνίδι είναι προσωρινά μη διαθέσιμο λόγω προγραμματισμένης συντήρησης. Αναμένουμε η συντήρηση να έχει ολοκληρωθεί έως τις 06/03/2024 03:00 CET."
                            },
                            {
                                "locale": "es_AR",
                                "content": "El juego no estará disponible durante un tiempo mientras llevamos a cabo tareas de mantenimiento. Esperamos terminar a las 05/03/2024 23:00 GMT-03."
                            },
                            {
                                "locale": "ko_KR",
                                "content": "정기 점검으로 인해 일시적으로 게임을 이용하실 수 없습니다. 점검은 2024/03/06 11:00 KST경 종료 예정입니다. 양해 부탁드립니다."
                            },
                            {
                                "locale": "hu_HU",
                                "content": "A játék a tervezett karbantartás ideje alatt átmenetileg nem érhető el. A karbantartás tervezett befejezése: 06/03/2024 03:00 CET."
                            },
                            {
                                "locale": "de_DE",
                                "content": "Das Spiel ist vorübergehend nicht verfügbar, da wir planmäßige Wartungsarbeiten durchführen. Wir gehen davon aus, dass diese Wartungsarbeiten bis 06/03/2024 02:00 GMT abgeschlossen sein werden."
                            },
                            {
                                "locale": "en_US",
                                "content": "The game is temporarily unavailable while we perform scheduled maintenance, we expect this maintenance to be complete by 03/05/2024 18:00 PST."
                            },
                            {
                                "locale": "th_TH",
                                "content": "ไม่สามารถเข้าเล่นเกมได้ชั่วคราวเนื่องจากการปิดปรับปรุงระบบตามที่กำหนดไว้ เราคาดว่าการปิดปรับปรุงระบบจะเสร็จสิ้นในเวลา 09:00 GMT+07 06/03/2024"
                            },
                            {
                                "locale": "pl_PL",
                                "content": "Gra jest tymczasowo niedostępna z powodu zaplanowanej przerwy technicznej. Przewidujemy, że przerwa techniczna potrwa do 06/03/2024 03:00 CET."
                            },
                            {
                                "locale": "zh_MY",
                                "content": "游戏正在进行例行维护，暂不可用。按照预定计划，维护将在10:00 GMT+08 06/03/2024前结束。"
                            }
                        ],
                        "publish_locations": [
                            "riotstatus",
                            "game",
                            "riotclient"
                        ]
                    }
                ],
                "platforms": [
                    "windows",
                    "macos",
                    "ios",
                    "android"
                ],
                "maintenance_status": "in_progress",
                "incident_severity": null
            }
        ],
        "incidents": []
    }

```

### MATCH-V5

Get a list of match ids by puuid

```py
@lol.get("/lol/match/v5/matches/by-puuid/{puuid}/ids")
async def get_matches_by_puuid(
    startTime: long | None = None, 
    endTime: long | None = None, 
    queue: int | None = None, 
    type: string | None = None, 
    start: int = 0, 
    count: int = 20
):

    # RESPONSE BODY
    return [
        "KR_6977481200",
        "KR_6977459092",
        "KR_6977398994",
        "KR_6976504732",
        "KR_6976487977",
        "KR_6972091977",
        "KR_6972025772",
        "KR_6970445718",
        "KR_6970398235",
        "KR_6970323185",
        "KR_6970260266",
        "KR_6969109404",
        "KR_6967843376",
        "KR_6967803792",
        "KR_6967779059",
        "KR_6967736244",
        "KR_6966840159",
        "KR_6966809650",
        "KR_6966318132",
        "KR_6965197239"
    ]
```

Get a match by match id

```py
@lol.get("/lol/match/v5/matches/{matchId}")

    # RESPONSE BODY
    return {
        "metadata": {
            "dataVersion": "2",
            "matchId": "KR_6977459092",
            "participants": [
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID"
            ]
        },
        "info": {
            "endOfGameResult": "GameComplete",
            "gameCreation": 1709651833292,
            "gameDuration": 1014,
            "gameEndTimestamp": 1709652872439,
            "gameId": 6977459092,
            "gameMode": "CLASSIC",
            "gameName": "teambuilder-match-6977459092",
            "gameStartTimestamp": 1709651858191,
            "gameType": "MATCHED_GAME",
            "gameVersion": "14.4.562.8237",
            "mapId": 11,
            "participants": [
                {
                    "allInPings": 0,
                    "assistMePings": 1,
                    "assists": 2,
                    "baronKills": 0,
                    "basicPings": 0,
                    "bountyLevel": 2,
                    "challenges": {
                        "12AssistStreakCount": 0,
                        "abilityUses": 165,
                        "acesBefore15Minutes": 0,
                        "alliedJungleMonsterKills": 0,
                        "baronTakedowns": 0,
                        "blastConeOppositeOpponentCount": 0,
                        "bountyGold": 0,
                        "buffsStolen": 0,
                        "completeSupportQuestInTime": 0,
                        "controlWardTimeCoverageInRiverOrEnemyHalf": 0.3645783611656552,
                        "controlWardsPlaced": 1,
                        "damagePerMinute": 655.0064163983399,
                        "damageTakenOnTeamPercentage": 0.2606794797519068,
                        "dancedWithRiftHerald": 0,
                        "deathsByEnemyChamps": 1,
                        "dodgeSkillShotsSmallWindow": 0,
                        "doubleAces": 0,
                        "dragonTakedowns": 1,
                        "earliestDragonTakedown": 900.1918416999999,
                        "earlyLaningPhaseGoldExpAdvantage": 0,
                        "effectiveHealAndShielding": 0,
                        "elderDragonKillsWithOpposingSoul": 0,
                        "elderDragonMultikills": 0,
                        "enemyChampionImmobilizations": 17,
                        "enemyJungleMonsterKills": 0,
                        "epicMonsterKillsNearEnemyJungler": 0,
                        "epicMonsterKillsWithin30SecondsOfSpawn": 0,
                        "epicMonsterSteals": 0,
                        "epicMonsterStolenWithoutSmite": 0,
                        "firstTurretKilled": 1,
                        "firstTurretKilledTime": 860.3535653,
                        "flawlessAces": 0,
                        "fullTeamTakedown": 0,
                        "gameLength": 1014.4556814,
                        "getTakedownsInAllLanesEarlyJungleAsLaner": 0,
                        "goldPerMinute": 441.19555743044015,
                        "hadOpenNexus": 0,
                        "highestChampionDamage": 1,
                        "immobilizeAndKillWithAlly": 6,
                        "initialBuffCount": 0,
                        "initialCrabCount": 0,
                        "jungleCsBefore10Minutes": 0,
                        "junglerTakedownsNearDamagedEpicMonster": 0,
                        "kTurretsDestroyedBeforePlatesFall": 0,
                        "kda": 7,
                        "killAfterHiddenWithAlly": 2,
                        "killParticipation": 0.4666666666666667,
                        "killedChampTookFullTeamDamageSurvived": 0,
                        "killingSprees": 1,
                        "killsNearEnemyTurret": 4,
                        "killsOnOtherLanesEarlyJungleAsLaner": 1,
                        "killsOnRecentlyHealedByAramPack": 0,
                        "killsUnderOwnTurret": 1,
                        "killsWithHelpFromEpicMonster": 0,
                        "knockEnemyIntoTeamAndKill": 0,
                        "landSkillShotsEarlyGame": 0,
                        "laneMinionsFirst10Minutes": 77,
                        "laningPhaseGoldExpAdvantage": 1,
                        "legendaryCount": 0,
                        "legendaryItemUsed": [
                            3110
                        ],
                        "lostAnInhibitor": 0,
                        "maxCsAdvantageOnLaneOpponent": 33,
                        "maxKillDeficit": 1,
                        "maxLevelLeadLaneOpponent": 2,
                        "mejaisFullStackInTime": 0,
                        "moreEnemyJungleThanOpponent": 0,
                        "multiKillOneSpell": 0,
                        "multiTurretRiftHeraldCount": 0,
                        "multikills": 0,
                        "multikillsAfterAggressiveFlash": 0,
                        "outerTurretExecutesBefore10Minutes": 0,
                        "outnumberedKills": 2,
                        "outnumberedNexusKill": 0,
                        "perfectDragonSoulsTaken": 0,
                        "perfectGame": 0,
                        "pickKillWithAlly": 6,
                        "playedChampSelectPosition": 1,
                        "poroExplosions": 0,
                        "quickCleanse": 0,
                        "quickFirstTurret": 0,
                        "quickSoloKills": 0,
                        "riftHeraldTakedowns": 0,
                        "saveAllyFromDeath": 0,
                        "scuttleCrabKills": 0,
                        "skillshotsDodged": 1,
                        "skillshotsHit": 0,
                        "snowballsHit": 0,
                        "soloBaronKills": 0,
                        "soloKills": 1,
                        "stealthWardsPlaced": 5,
                        "survivedSingleDigitHpCount": 0,
                        "survivedThreeImmobilizesInFight": 2,
                        "takedownOnFirstTurret": 0,
                        "takedowns": 7,
                        "takedownsAfterGainingLevelAdvantage": 0,
                        "takedownsBeforeJungleMinionSpawn": 0,
                        "takedownsFirstXMinutes": 6,
                        "takedownsInAlcove": 0,
                        "takedownsInEnemyFountain": 0,
                        "teamBaronKills": 0,
                        "teamDamagePercentage": 0.2563716981601064,
                        "teamElderDragonKills": 0,
                        "teamRiftHeraldKills": 0,
                        "tookLargeDamageSurvived": 0,
                        "turretPlatesTaken": 2,
                        "turretTakedowns": 0,
                        "turretsTakenWithRiftHerald": 0,
                        "twentyMinionsIn3SecondsCount": 0,
                        "twoWardsOneSweeperCount": 0,
                        "unseenRecalls": 0,
                        "visionScoreAdvantageLaneOpponent": -0.47374266386032104,
                        "visionScorePerMinute": 0.5174998465781803,
                        "wardTakedowns": 0,
                        "wardTakedownsBefore20M": 0,
                        "wardsGuarded": 0
                    },
                    "champExperience": 9769,
                    "champLevel": 12,
                    "championId": 24,
                    "championName": "Jax",
                    "championTransform": 0,
                    "commandPings": 2,
                    "consumablesPurchased": 2,
                    "damageDealtToBuildings": 915,
                    "damageDealtToObjectives": 1272,
                    "damageDealtToTurrets": 915,
                    "damageSelfMitigated": 16153,
                    "dangerPings": 0,
                    "deaths": 1,
                    "detectorWardsPlaced": 1,
                    "doubleKills": 0,
                    "dragonKills": 0,
                    "eligibleForProgression": true,
                    "enemyMissingPings": 1,
                    "enemyVisionPings": 3,
                    "firstBloodAssist": false,
                    "firstBloodKill": false,
                    "firstTowerAssist": false,
                    "firstTowerKill": false,
                    "gameEndedInEarlySurrender": false,
                    "gameEndedInSurrender": true,
                    "getBackPings": 6,
                    "goldEarned": 7459,
                    "goldSpent": 6225,
                    "holdPings": 0,
                    "individualPosition": "MIDDLE",
                    "inhibitorKills": 0,
                    "inhibitorTakedowns": 0,
                    "inhibitorsLost": 0,
                    "item0": 2031,
                    "item1": 3110,
                    "item2": 3057,
                    "item3": 3047,
                    "item4": 1055,
                    "item5": 3044,
                    "item6": 3340,
                    "itemsPurchased": 9,
                    "killingSprees": 2,
                    "kills": 5,
                    "lane": "NONE",
                    "largestCriticalStrike": 0,
                    "largestKillingSpree": 3,
                    "largestMultiKill": 1,
                    "longestTimeSpentLiving": 477,
                    "magicDamageDealt": 28829,
                    "magicDamageDealtToChampions": 4968,
                    "magicDamageTaken": 2362,
                    "missions": {
                        "playerScore0": 0,
                        "playerScore1": 0,
                        "playerScore10": 0,
                        "playerScore11": 0,
                        "playerScore2": 0,
                        "playerScore3": 0,
                        "playerScore4": 0,
                        "playerScore5": 0,
                        "playerScore6": 0,
                        "playerScore7": 0,
                        "playerScore8": 0,
                        "playerScore9": 0
                    },
                    "needVisionPings": 0,
                    "neutralMinionsKilled": 0,
                    "nexusKills": 0,
                    "nexusLost": 0,
                    "nexusTakedowns": 0,
                    "objectivesStolen": 0,
                    "objectivesStolenAssists": 0,
                    "onMyWayPings": 8,
                    "participantId": 8,
                    "pentaKills": 0,
                    "perks": {
                        "statPerks": {
                            "defense": 5001,
                            "flex": 5008,
                            "offense": 5005
                        },
                        "styles": [
                            {
                                "description": "primaryStyle",
                                "selections": [
                                    {
                                        "perk": 8008,
                                        "var1": 17,
                                        "var2": 3,
                                        "var3": 0
                                    },
                                    {
                                        "perk": 8009,
                                        "var1": 1530,
                                        "var2": 0,
                                        "var3": 0
                                    },
                                    {
                                        "perk": 9104,
                                        "var1": 12,
                                        "var2": 40,
                                        "var3": 0
                                    },
                                    {
                                        "perk": 8299,
                                        "var1": 455,
                                        "var2": 0,
                                        "var3": 0
                                    }
                                ],
                                "style": 8000
                            },
                            {
                                "description": "subStyle",
                                "selections": [
                                    {
                                        "perk": 8345,
                                        "var1": 3,
                                        "var2": 0,
                                        "var3": 0
                                    },
                                    {
                                        "perk": 8347,
                                        "var1": 0,
                                        "var2": 0,
                                        "var3": 0
                                    }
                                ],
                                "style": 8300
                            }
                        ]
                    },
                    "physicalDamageDealt": 37482,
                    "physicalDamageDealtToChampions": 6106,
                    "physicalDamageTaken": 11069,
                    "placement": 0,
                    "playerAugment1": 0,
                    "playerAugment2": 0,
                    "playerAugment3": 0,
                    "playerAugment4": 0,
                    "playerScore0": 0,
                    "playerScore1": 0,
                    "playerScore10": 0,
                    "playerScore11": 0,
                    "playerScore2": 0,
                    "playerScore3": 0,
                    "playerScore4": 0,
                    "playerScore5": 0,
                    "playerScore6": 0,
                    "playerScore7": 0,
                    "playerScore8": 0,
                    "playerScore9": 0,
                    "playerSubteamId": 0,
                    "profileIcon": 6,
                    "pushPings": 0,
                    "puuid": "encryptedPUUID",
                    "quadraKills": 0,
                    "riotIdGameName": "Hide on bush",
                    "riotIdTagline": "KR1",
                    "role": "SUPPORT",
                    "sightWardsBoughtInGame": 0,
                    "spell1Casts": 40,
                    "spell2Casts": 49,
                    "spell3Casts": 71,
                    "spell4Casts": 5,
                    "subteamPlacement": 0,
                    "summoner1Casts": 3,
                    "summoner1Id": 12,
                    "summoner2Casts": 1,
                    "summoner2Id": 4,
                    "summonerId": "encryptedSummonerId",
                    "summonerLevel": 748,
                    "summonerName": "Hide on bush",
                    "teamEarlySurrendered": false,
                    "teamId": 200,
                    "teamPosition": "MIDDLE",
                    "timeCCingOthers": 17,
                    "timePlayed": 1014,
                    "totalAllyJungleMinionsKilled": 0,
                    "totalDamageDealt": 66312,
                    "totalDamageDealtToChampions": 11074,
                    "totalDamageShieldedOnTeammates": 0,
                    "totalDamageTaken": 13479,
                    "totalEnemyJungleMinionsKilled": 0,
                    "totalHeal": 1412,
                    "totalHealsOnTeammates": 0,
                    "totalMinionsKilled": 150,
                    "totalTimeCCDealt": 1213,
                    "totalTimeSpentDead": 16,
                    "totalUnitsHealed": 1,
                    "tripleKills": 0,
                    "trueDamageDealt": 0,
                    "trueDamageDealtToChampions": 0,
                    "trueDamageTaken": 48,
                    "turretKills": 0,
                    "turretTakedowns": 0,
                    "turretsLost": 0,
                    "unrealKills": 0,
                    "visionClearedPings": 0,
                    "visionScore": 8,
                    "visionWardsBoughtInGame": 1,
                    "wardsKilled": 0,
                    "wardsPlaced": 6,
                    "win": true
                },
            ],
            "platformId": "KR",
            "queueId": 420,
            "teams": [
                {
                    "bans": [
                        {
                            "championId": 43,
                            "pickTurn": 1
                        },
                        {
                            "championId": 518,
                            "pickTurn": 2
                        },
                        {
                            "championId": 114,
                            "pickTurn": 3
                        },
                        {
                            "championId": 55,
                            "pickTurn": 4
                        },
                        {
                            "championId": 360,
                            "pickTurn": 5
                        }
                    ],
                    "objectives": {
                        "baron": {
                            "first": false,
                            "kills": 0
                        },
                        "champion": {
                            "first": true,
                            "kills": 7
                        },
                        "dragon": {
                            "first": false,
                            "kills": 0
                        },
                        "horde": {
                            "first": true,
                            "kills": 3
                        },
                        "inhibitor": {
                            "first": false,
                            "kills": 0
                        },
                        "riftHerald": {
                            "first": true,
                            "kills": 1
                        },
                        "tower": {
                            "first": false,
                            "kills": 0
                        }
                    },
                    "teamId": 100,
                    "win": false
                },
                {
                    "bans": [
                        {
                            "championId": -1,
                            "pickTurn": 6
                        },
                        {
                            "championId": 163,
                            "pickTurn": 7
                        },
                        {
                            "championId": -1,
                            "pickTurn": 8
                        },
                        {
                            "championId": 126,
                            "pickTurn": 9
                        },
                        {
                            "championId": -1,
                            "pickTurn": 10
                        }
                    ],
                    "objectives": {
                        "baron": {
                            "first": false,
                            "kills": 0
                        },
                        "champion": {
                            "first": false,
                            "kills": 15
                        },
                        "dragon": {
                            "first": true,
                            "kills": 2
                        },
                        "horde": {
                            "first": false,
                            "kills": 2
                        },
                        "inhibitor": {
                            "first": false,
                            "kills": 0
                        },
                        "riftHerald": {
                            "first": false,
                            "kills": 0
                        },
                        "tower": {
                            "first": true,
                            "kills": 2
                        }
                    },
                    "teamId": 200,
                    "win": true
                }
            ],
            "tournamentCode": ""
        }
    }
```

Get a match timeline by match id

```py
@lol.get("/lol/match/v5/matches/{matchId}/timeline")

    # RESPONSE BODY
    return {
        "metadata": {
            "dataVersion": "2",
            "matchId": "KR_6977459092",
            "participants": [
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID",
                "encryptedPUUID"
            ]
        },
        "info": {
            "endOfGameResult": "GameComplete",
            "frameInterval": 60000,
            "frames": [
                {
                    "events": [
                        {
                            "realTimestamp": 1709651858093,
                            "timestamp": 0,
                            "type": "PAUSE_END"
                        }
                    ],
                    "participantFrames": {
                        "8": {
                            "championStats": {
                                "abilityHaste": 0,
                                "abilityPower": 0,
                                "armor": 36,
                                "armorPen": 0,
                                "armorPenPercent": 0,
                                "attackDamage": 25,
                                "attackSpeed": 100,
                                "bonusArmorPenPercent": 0,
                                "bonusMagicPenPercent": 0,
                                "ccReduction": 0,
                                "cooldownReduction": 0,
                                "health": 665,
                                "healthMax": 665,
                                "healthRegen": 0,
                                "lifesteal": 0,
                                "magicPen": 0,
                                "magicPenPercent": 0,
                                "magicResist": 32,
                                "movementSpeed": 350,
                                "omnivamp": 0,
                                "physicalVamp": 0,
                                "power": 339,
                                "powerMax": 339,
                                "powerRegen": 0,
                                "spellVamp": 0
                            },
                            "currentGold": 500,
                            "damageStats": {
                                "magicDamageDone": 0,
                                "magicDamageDoneToChampions": 0,
                                "magicDamageTaken": 0,
                                "physicalDamageDone": 0,
                                "physicalDamageDoneToChampions": 0,
                                "physicalDamageTaken": 0,
                                "totalDamageDone": 0,
                                "totalDamageDoneToChampions": 0,
                                "totalDamageTaken": 0,
                                "trueDamageDone": 0,
                                "trueDamageDoneToChampions": 0,
                                "trueDamageTaken": 0
                            },
                            "goldPerSecond": 0,
                            "jungleMinionsKilled": 0,
                            "level": 1,
                            "minionsKilled": 0,
                            "participantId": 8,
                            "position": {
                                "x": 14503,
                                "y": 14275
                            },
                            "timeEnemySpentControlled": 0,
                            "totalGold": 500,
                            "xp": 0
                        },
                    },
                    "timestamp": 0
                },
            ],
            "gameId": 6977459092,
            "participants": [
                {
                    "participantId": 8,
                    "puuid": "encryptedPUUID"
                },
            ]
        }
    }
```

## Duo Restrictions

### Iron

- Iron
- Bronze
- Silver

### Bronze

- Iron
- Bronze
- Sliver

### Silver

- Iron
- Bronze
- Silver
- Gold

### Gold

- Silver
- Gold
- Platinum

### Platinum

- Gold
- Platinum
- Emerald

### Emerald

- Platinum
- Emerald
- Diamond (within two divisions)

### Diamond

- Emerald (within two divisions)
- Diamond (within two divisions)

### Solo Only

- Master
- Grandmaster
- Challenger
