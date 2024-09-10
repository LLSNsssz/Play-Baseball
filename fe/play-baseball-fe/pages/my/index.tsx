import React, { useState, useEffect, useRef} from 'react';
import MyProfile from '@/components/MyProfile';
import Wrapper from '@/components/Wrapper'
import api from '@/constants/axios';
import { User } from '@/constants/types'
import { MEMBER_RESIGN } from '@/constants/endpoints'

const My: React.FC = () => {
    const initialUser: User = {
        id: -1,
        email: "",
        nickname: "",
        role: "",
        createdAt: "",
        updatedAt: "",
        lastLoginDate: "",
        deletedAt: "",
        emailVerified: false
    };

    const [userState, setUser] = useState<User>(initialUser);

    useEffect(() => {
        try {
            api.get<User>(MEMBER_RESIGN).then(res => {
                const userData = res.data.data;

                const user: User = {
                    id: userData.id,
                    email: userData.email,
                    nickname: userData.nickname,
                    role: userData.role,
                    createdAt: userData.createdAt,
                    updatedAt: userData.updatedAt,
                    lastLoginDate: userData.lastLoginDate,
                    deletedAt: userData.deletedAt,
                    // createdAt: new Date(userData.createdAt),
                    // updatedAt: new Date(userData.updatedAt),
                    // lastLoginDate: new Date(userData.lastLoginDate),
                    // deletedAt: new Date(userData.deletedAt),
                    emailVerified: userData.emailVerified,
                };
                
                setUser(user);
                console.log("User loaded! " + res.data);
                console.log("User is " + userState.nickname);
            })
          } catch (err) {
            if(err instanceof Error) {
                console.error("Error fetching user data: " + err.message);
                setUser(initialUser)
            }
          }
    }, []);

    return (
        <Wrapper>
            <MyProfile user={userState}/>
        </Wrapper>
    );
};

export default My;

function componentDidMount() {
    throw new Error('Function not implemented.');
}
  