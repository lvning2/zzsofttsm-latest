package com.zzsoft.zzsofttsm.service;

import com.zzsoft.zzsofttsm.entity.*;
import com.zzsoft.zzsofttsm.mapper.ComputerMapper;
import com.zzsoft.zzsofttsm.repository.*;
import com.zzsoft.zzsofttsm.vo.ComputerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ComputerService {

    @Autowired
    private ComputerRepository computerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GraphicsCardRepository graphicsCardRepository;

    @Autowired
    private CpuRepository cpuRepository;

    @Autowired
    private HardDiskRepository hardDiskRepository;

    public List findAll(){                          //查询所有电脑设备
        return computerRepository.findAll();
    }

    public void deleteById(String id){             //删除一台设备
        computerRepository.deleteById(id);
    }

    public Computer seveOne(Computer computer){     //添加一台设备
        return computerRepository.save(computer);
    }

    public Computer findOne(String id){        //查询一个电脑
        Optional<Computer> byId = computerRepository.findById(id);
        return byId.orElse(null);
    }

//    public void delHdOfComputer(Integer cid,Integer hdid){     //移除一个电脑的硬盘
//
//        Computer computer = computerRepository.getOne(cid);
//
//        List<HardDisk> hds = computer.getHdId();
//        for (int i=0;i<hds.size();i++){
//            if (hds.get(i).getId().equals(hdid)){
//               hds.remove(i);
//               break;
//            }
//        }
//
//        computerRepository.save(computer);
//    }

//    public Computer addHdToComputer(Integer cid,Integer hdid){      //将一块硬盘添加到一个电脑设备
//        Computer computer = computerRepository.getOne(cid);
//        HardDisk one = hardDiskRepository.getOne(hdid);
//        computer.getHdId().add(one);
//        Computer save = computerRepository.save(computer);
//        return save;
//    }

    public void updateComputerPerson(String cid,Integer uid){
        Computer one = computerRepository.getOne(cid);
        one.setPersonLiableId(uid);
    }

    @Transactional
    public List<ComputerVo> getByPersonLiableId(Integer personLiableId){      // 根据责任人获取所有的设备
        User user = userRepository.getOne(personLiableId);
        List<Computer> computers = computerRepository.findByPersonLiableId(personLiableId);
        List<ComputerVo> computerVos = ComputerMapper.toVoList(computers);

        for (ComputerVo computerVo: computerVos){
            computerVo.setPersonName(user.getName());
        }
        return computerVos;
    }

    @Transactional
    public List<ComputerVo> getAllEquipment(){      // 获取所有的电脑设备
        List<Computer> computers = computerRepository.findAll(Sort.by("equipmentNumber"));
        List<ComputerVo> computerVos = ComputerMapper.toVoList(computers);
        for(ComputerVo computerVo: computerVos){
            User user = userRepository.getOne(computerVo.getPersonLiableId());
            computerVo.setPersonName(user.getName());
        }
        return computerVos;
    }

    @Transactional
    public boolean existEquipmentNumber(String equipmentNumber){
        return computerRepository.existsByEquipmentNumber(equipmentNumber);
    }

    @Transactional
    public ComputerVo getByEquipmentById(String id){             // 通过电脑设备的id获取详细信息
        Computer computer = computerRepository.getOne(id);
        ComputerVo computerVo = ComputerMapper.toVo(computer);
        User user = userRepository.getOne(computerVo.getPersonLiableId());
        computerVo.setPersonName(user.getName());
        return computerVo;
    }

    @Transactional
    public void insertEquipment(Computer computer){         // 添加一台电脑设备
        computerRepository.save(computer);
    }

    @Transactional
    public void deleteEquipmentById(String id){      // 根据id删除一个电脑设备
        Computer computer = computerRepository.getOne(id);
        computerRepository.delete(computer);
    }


    @Transactional
    public void insertGraphicsCard(String name){            // 添加一个显卡
        GraphicsCard graphicsCard = new GraphicsCard();
        graphicsCard.setName(name);
        graphicsCardRepository.save(graphicsCard);
    }

    @Transactional
    public List<GraphicsCard> getAllGraphicsCard(){     // 获取所有显卡
        return  graphicsCardRepository.findAll();
    }

    @Transactional
    public void insertCpu(String name){            // 插入一个cpu
        Cpu cpu=new Cpu();
        cpu.setName(name);
        cpuRepository.save(cpu);
    }

    @Transactional
    public List<Cpu> getAllCpu(){           // 获取所有cpu
        return cpuRepository.findAll();
    }

    @Transactional
    public void updateComputerId(ComputerVo computerVo){    // 根据id更新电脑设备
        Computer computer = computerRepository.getOne(computerVo.getId());
        Computer c = ComputerMapper.fromVo(computerVo);
        //System.out.println("打印"+c);
//        computer.setEquipmentNumber(computerVo.getEquipmentNumber());
//        computer.setEquipmentType(computerVo.getEquipmentType());
//        computer.setHdCapacity(computerVo.getHdCapacity());
//        computer.setIpAddress(computerVo.getIpAddress());
//        computer.setOsVersion(computerVo.getOsVersion());       // todo
//        computer.setOsInstallTime(computerVo.getOsInstallTime());
//        computer.setRemark(computerVo.getRemark());
//        computer.setIsNetwork(computerVo.getIsNetwork());
//        computer.setCpuName(computerVo.getCpuName());
//        computer.setGraphicsCardName(computerVo.getGraphicsCardName());
//        computer.setMemory(computerVo.getMemory());
//        computer.setLockNumber(computerVo.getLockNumber());
//        computer.setMonitor(computerVo.getMonitor());
        computerRepository.save(c);
    }


}



